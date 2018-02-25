package com.carsoline.rest;

import com.carsoline.rest.daos.Engine;
import com.carsoline.rest.dataAssemblers.EngineDataAssembler;
import com.carsoline.rest.dataAssemblers.EntryDataAssembler;
import com.carsoline.rest.dtos.CarDto;
import com.carsoline.rest.dtos.EngineDto;
import com.carsoline.rest.dtos.EntryDto;
import com.carsoline.rest.exceptions.AppException;
import com.carsoline.rest.exceptions.MySQLJDBCException;
import com.carsoline.services.CarService;
import com.carsoline.services.EngineService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Path("/engines")
@Api(value = "/engines", description = "Operations on engines", authorizations = {@Authorization(value="basicAuth")})
public class EngineResource {

    private EngineService engineService;

    private CarService carService;

    @Autowired
    private EngineResource(EngineService engineService, CarService carService) {
        this.engineService = engineService;
        this.carService = carService;
    }

    @RolesAllowed( {"USER", "ADMIN"} )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get engines collection", notes = "Get engines collection",
            response = EngineDto.class,
            responseContainer = "LIST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation succesful", response = EngineDto.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getEngines(
            @ApiParam("car id") @QueryParam("carId") Long carId,
            @ApiParam("fuel type") @QueryParam("fuelType") String fuelType
    ){
        GenericEntity<Collection<EngineDto>> engineCollection;

        if (carId != null) {
            if(fuelType != null) {
                engineCollection = new GenericEntity<Collection<EngineDto>>(
                        EngineDataAssembler.toEngineDtoCollection(engineService.findByCarId(carId))){};
            } else {
                engineCollection = new GenericEntity<Collection<EngineDto>>(
                        EngineDataAssembler.toEngineDtoCollection(engineService.findByCarId(carId))){};
            }
        } else if(fuelType != null) {
            engineCollection = new GenericEntity<Collection<EngineDto>>(
                    EngineDataAssembler.toEngineDtoCollection(engineService.findByCarId(carId))){};
        } else {
                engineCollection =
                        new GenericEntity<Collection<EngineDto>>(
                                EngineDataAssembler.toEngineDtoCollection(
                                        engineService.getEngines())){};
            }

        return Response.ok(engineCollection).cacheControl(CacheControl.valueOf("30")).build();
    }

    @RolesAllowed( {"USER", "ADMIN"} )
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get engine by its id", notes = "Gets engine by its id",
            response = EngineDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation succesful", response = EngineDto.class),
            @ApiResponse(code = 404, message = "Engine with specified id not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getEngineById(@PathParam("id") final Long id) throws AppException {
        Engine engine = engineService.findById(id);

        if (engine == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "Engine with specified id " + id + " doesn't exist in the database!",
                    "Verify the existence of the engine with the id " + id + " in the database");
        }
        EngineDto engineDto = EngineDataAssembler.toEngineDto(engineService.findById(id));

        return Response.ok(engineDto).build();
    }

    @RolesAllowed( {"USER", "ADMIN"})
    @GET
    @Path("/{id}/entries")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get entries for particular engine",
            response = EntryDto.class,
            responseContainer = "LIST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation succesful", response = EntryDto.class, responseContainer = "LIST"),
            @ApiResponse(code = 404, message = "Engine with specified id not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getEntriesByEngineId(@PathParam("id") final Long id) throws AppException{
        Engine engine = engineService.findById(id);

        if(engine == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "Engine with specified id " + id + " doesn't exist in the database!",
                    "Verify the existence of the engine with the id " + id + " in the database");
        }

        GenericEntity<Collection<EntryDto>> entryCollection = new GenericEntity<Collection<EntryDto>>(
                EntryDataAssembler.toEntryDtoCollection(
                        engine.getEntries())){};

        return Response.ok(entryCollection).build();
    }

    @RolesAllowed("ADMIN")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Creates new engine.",
            response = Engine.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Engine created.", response = Engine.class),
            @ApiResponse(code = 409, message = "Specified car by id not found in database"),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
    public Response addNewEngine (Engine engine) throws URISyntaxException, AppException, MySQLJDBCException {

        if(engine.getCar().getId() != null) {
            if(carService.findOneById(engine.getCar().getId()) == null) {
                throw new AppException(Response.Status.CONFLICT.getStatusCode(), "Car with specified id doesn't exist in the database!",
                        "Verify the existence of the car with the id  in the database");
            }
        }
        Engine engineToSave = engineService.save(engine);

        return Response.created(new URI("/engines/" + engineToSave.getId()))
                    .entity(EngineDataAssembler.toEngineDto(engineToSave))
                    .status(Response.Status.CREATED)
                    .build();


    }

    @RolesAllowed("ADMIN")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Updates engine",
            response = Engine.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Engine updated.", response = Engine.class),
            @ApiResponse(code = 404, message = "Engine with specified id not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response updateEngine (Engine engine) throws URISyntaxException, AppException, MySQLJDBCException {
        Engine engineToUpdate = engineService.findById(engine.getId());

        if (engineToUpdate == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "Engine with specified id " + engine.getId() + " doesn't exist in the database!",
                    "Verify the existence of the engine with the id " + engine.getId() + " in the database");
        }

        engineToUpdate.setCar(engine.getCar());
        engineToUpdate.setEngineSize(engine.getEngineSize());
        engineToUpdate.setEngineSymbol(engine.getEngineSymbol());
        engineToUpdate.setFuelType(engine.getFuelType());
        engineToUpdate.setEnginePower(engine.getEnginePower());
        engineToUpdate.setEntries(engine.getEntries());
        engineService.save(engineToUpdate);

        return Response.ok(EngineDataAssembler.toEngineDto(engineService.findById(engine.getId()))).build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Deletes engine with specified id."
    )
    @ApiResponses( value = {
            @ApiResponse(code = 204, message = "Engine deleted succesfully."),
            @ApiResponse(code = 404, message = "Engine with specified id not found.", response = AppException.class),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
    public Response deleteEngine (@PathParam("id") final Long id) throws URISyntaxException, AppException {

        if(engineService.findById(id) == null) throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "Engine with specified id " + id + " doesn't exist in the database!",
                "Verify the existence of the engine with the id " + id + " in the database");

        engineService.delete(id);

        return Response.noContent().build();
    }
}
