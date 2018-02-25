package com.carsoline.rest;

import com.carsoline.rest.daos.Car;
import com.carsoline.rest.daos.Engine;
import com.carsoline.rest.dataAssemblers.CarDataAssembler;
import com.carsoline.rest.dataAssemblers.EngineDataAssembler;
import com.carsoline.rest.dtos.CarDto;
import com.carsoline.rest.dtos.EngineDto;
import com.carsoline.rest.exceptions.AppException;
import com.carsoline.rest.exceptions.ErrorMessage;
import com.carsoline.services.CarService;
import com.carsoline.services.EngineService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@Path("/cars")
@Api(value = "/cars", description = "Operations on cars")
public class CarResource {

    private CarService carService;

    @Autowired
    private CarResource(CarService carService) {
        this.carService = carService;
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GET
    @Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @ApiOperation(value = "Get cars collection", notes = "Get cars collection", response = CarDto.class, responseContainer = "LIST", authorizations = {@Authorization(value="basicAuth")} )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation succesful", response = CarDto.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getCarsCollection(
            @ApiParam("brand of cars") @QueryParam("brand") String brand,
            @ApiParam("model of cars") @QueryParam("model") String model,
            @ApiParam("return content type") @HeaderParam("content-type") String contentType){

        GenericEntity<Collection<CarDto>> carCollection;

        if (brand != null) {
            if(model != null) {
                carCollection = new GenericEntity<Collection<CarDto>>(
                        CarDataAssembler.toCarDtoCollection(carService.findByBrandAndModel(brand, model))){};
            }
            else {
                carCollection = new GenericEntity<Collection<CarDto>>(
                        CarDataAssembler.toCarDtoCollection(carService.findByBrand(brand))) {
                };
            }
        } else {
            carCollection = new GenericEntity<Collection<CarDto>>(
                    CarDataAssembler.toCarDtoCollection(carService.getCars())){};
        }

        String mediaType = "application/xml".equals(contentType) ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON;

        return Response.ok(carCollection, mediaType).cacheControl(CacheControl.valueOf("30")).build();
    }

    @RolesAllowed( {"USER","ADMIN"} )
    @GET
    @Path("/searchByProductionDatesBetween")
    @ApiOperation(value = "Get cars between specified production start and end date", response = CarDto.class, authorizations = {@Authorization(value="basicAuth")})
    @Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation succesful", response = CarDto.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getCarsByProductionDateBetween(
            @ApiParam("production start date") @QueryParam("productionStartDate") final Integer productionStartDate,
            @ApiParam("production end date") @QueryParam("productionEndDate") final Integer productionEndDate,
            @ApiParam("return content type") @HeaderParam("Content-Type") String contentType) throws AppException {
        GenericEntity<Collection<CarDto>> carCollection;

        String mediaType = "application/xml".equals(contentType) ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON;

        if(productionStartDate != null) {
            if (productionEndDate != null) {
                carCollection = new GenericEntity<Collection<CarDto>>(
                        CarDataAssembler.toCarDtoCollection(
                                carService.findByProductionStartDateGreaterThanEqualAndProductionEndDateLessThanEqual(
                                        productionStartDate, productionEndDate))) {
                };

                return Response.ok(carCollection, mediaType).build();
            } else {
                throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "Value of production end date is missing!",
                        "Enter valid query parameter for production end date.");
            }
        } else {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "Value of production start date is missing!",
                    "Enter valid query parameter for production start date.");
        }
    }

    @RolesAllowed( {"USER","ADMIN"} )
    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get car by id", response = CarDto.class, authorizations = {@Authorization(value="basicAuth")})
    @Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation succesful", response = CarDto.class),
            @ApiResponse(code = 404, message = "Car with specified id no found", response = AppException.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getCarById(@ApiParam("id of a car") @PathParam("id") final Long id,
                               @ApiParam("return content type") @HeaderParam("Content-Type") String contentType) throws AppException {
        Car car = carService.findOneById(id);

        String mediaType = "application/xml".equals(contentType) ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON;

        if (car == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "Car with specified id " + id + " doesn't exist in the database!",
                    "Verify the existence of the car with the id " + id + " in the database");
        }

        CarDto carDto = CarDataAssembler.toCarDto(car);
        return Response.ok(carDto, mediaType).build();
    }

    @RolesAllowed( {"USER","ADMIN"} )
    @GET
    @Path("/{id}/engines")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get engines collection for car", response = EngineDto.class, responseContainer = "LIST", authorizations = {@Authorization(value="basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation succesful", response = EngineDto.class, responseContainer = "LIST"),
            @ApiResponse(code = 404, message = "Car with specified id doesn't exist in database", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getEngines(@ApiParam("id of a car") @PathParam("id") final Long id) throws AppException {
        Car car = carService.findOneById(id);

        if(car == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "Car with specified id " + id + " doesn't exist in the database!",
                    "Verify the existence of the car with the id " + id + " in the database");
        }

        GenericEntity<Collection<EngineDto>> engineCollection = new GenericEntity<Collection<EngineDto>>(EngineDataAssembler.toEngineDtoCollection(car.getEngines())){};

        return Response.ok(engineCollection).build();
    }

    @RolesAllowed("ADMIN")
    @POST
    @Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @ApiOperation(value = "Create new car", response = Car.class, authorizations = {@Authorization(value="basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car created", response = Car.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response addNewCar (Car carDto,
                               @ApiParam("Accept content type") @HeaderParam("Accept") String accept) throws URISyntaxException, AppException {

        carDto.setId(0l);
        Car car = carService.save(carDto);

        String acceptMediaType = "application/xml".equals(accept) ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON;

        return Response.created(new URI("/cars/"+car.getId()))
                .entity(car)
                .type(acceptMediaType)
                .status(Response.Status.CREATED)
                .build();
    }

    @RolesAllowed("ADMIN")
    @PUT
    @Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @ApiOperation(value = "Update car with specified id", response = Car.class, authorizations = {@Authorization(value="basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Car updated", response = Car.class),
            @ApiResponse(code = 404, message = "Car with specified id not found in the database", response = Car.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response updateCar (Car carDto,
                               @ApiParam("Accept content type") @HeaderParam("Accept") String accept) throws URISyntaxException, AppException {
        Car car = carService.findOneById(carDto.getId());

        String acceptMediaType = "application/xml".equals(accept) ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON;

        if (car != null) {
            car.setBrand(carDto.getBrand());
            car.setEngines(carDto.getEngines());
            car.setModel(carDto.getModel());
            car.setProductionStartDate(carDto.getProductionStartDate());
            car.setProductionEndDate(carDto.getProductionEndDate());
            car.setModel(carDto.getModel());

            carService.save(car);
            return Response.ok(CarDataAssembler.toCarDto(carService.findOneById(carDto.getId())), acceptMediaType).build();
        } else throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "Car with specified id " + carDto.getId() + " doesn't exist in the database!",
                "Verify the existence of the car with the id " + carDto.getId() + " in the database");

    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Path("/{id}")
    @Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @ApiOperation(value = "Delete car", authorizations = {@Authorization(value="basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Car deleted succesfully"),
            @ApiResponse(code = 404, message = "Car with specified id not found in the database."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response deleteCar (@ApiParam("id of a car") @PathParam("id") final Long id) throws URISyntaxException, AppException {
        if(carService.findOneById(id) != null) {
            carService.delete(id);
            return Response.noContent().build();
        } else throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "Car with specified id " + id + " doesn't exist in the database!",
                "Verify the existence of the car with the id " + id + " in the database");
    }
}