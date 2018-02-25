package com.carsoline.rest;

import com.carsoline.rest.daos.Entry;
import com.carsoline.rest.dataAssemblers.EntryDataAssembler;
import com.carsoline.rest.dtos.EngineDto;
import com.carsoline.rest.dtos.EntryDto;
import com.carsoline.rest.exceptions.AppException;
import com.carsoline.services.EntryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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

@Path("/entries")
@Api(value = "/entries", description = "Operations on engine gasoline usage entries", authorizations = {@Authorization(value="basicAuth")})
public class EntryResource {

    private EntryService entryService;

    @Autowired
    private EntryResource(EntryService entryService) { this.entryService = entryService; }

    @RolesAllowed( {"USER", "ADMIN"} )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get entries collection",
            notes = "Get entries collection",
            response = EntryDto.class,
            responseContainer = "LIST"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation succesful", response = EntryDto.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getEntriesCollection(
            @QueryParam("userId") String userId,
            @QueryParam("engineId") Long engineId) throws AppException {
        if(userId != null) {
            if(engineId != null) return
                    Response.ok(EntryDataAssembler.toEntryDto(entryService.findByUserIdAndEngineId(userId, engineId))).build();

            return Response.ok(new GenericEntity<Collection<EntryDto>>(
                            EntryDataAssembler.toEntryDtoCollection(entryService.findByUserId(userId))
                    ){}
            ).build();

        } else if(engineId != null) {

            return Response.ok(
                    new GenericEntity<Collection<EntryDto>>(
                            EntryDataAssembler.toEntryDtoCollection(entryService.findByEngineId(engineId)))
                    {})
                    .build();
        }

        return Response.ok(
                new GenericEntity<Collection<EntryDto>>(
                        EntryDataAssembler.toEntryDtoCollection(entryService.getEntries()))
                {})
                .build();
    }

    @RolesAllowed( {"USER", "ADMIN"} )
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get entry by its id", notes = "Gets entry by its id",
            response = EngineDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation succesful", response = EngineDto.class),
            @ApiResponse(code = 404, message = "Entry with specified id not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getEngineById(@PathParam("id") final String id) throws AppException {
        Entry entry = entryService.findById(id);

        if (entry == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "Engine with specified id " + id + " doesn't exist in the database!",
                    "Verify the existence of the engine with the id " + id + " in the database");
        }

        EntryDto entryDto = EntryDataAssembler.toEntryDto(entryService.findById(id));

        return Response.ok(entryDto).build();
    }

    @RolesAllowed( { "ADMIN", "USER" } )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create new entry",
            response = Entry.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entry created", response = Entry.class),
            @ApiResponse(code = 409, message = "Entry with specified engine id already exists in database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response addNewEntry (Entry entry) throws URISyntaxException, AppException {
        entry.setId(UUID.randomUUID().toString());

        if(entryService.findByUserIdAndEngineId(entry.getUser().getId(), entry.getEngine().getId()) == null) {
            Entry savedEntry = entryService.save(entry);

            return Response.created(new URI("/entries/"+savedEntry.getId()))
                    .entity(EntryDataAssembler.toEntryDto(savedEntry))
                    .status(Response.Status.CREATED)
                    .build();
        } else throw new AppException(Response.Status.CONFLICT.getStatusCode(), "Entry with engine id " + entry.getEngine().getId() + "already exists in database. You can post entry only once for particular engine" ,
                "");

    }

    @RolesAllowed( { "ADMIN", "USER" } )
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update entry with specified id",
            response = Entry.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry updated", response = Entry.class),
            @ApiResponse(code = 404, message = "Entry with specified id not found in database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response updateEntry (Entry entry) throws URISyntaxException, AppException {
        Entry entryToUpdate = entryService.findById(entry.getId());

        if (entryToUpdate != null) {
            entryToUpdate.setCityValue(entry.getCityValue());
            entryToUpdate.setEngine(entry.getEngine());
            entryToUpdate.setHighwayValue(entry.getHighwayValue());
            entryToUpdate.setUser(entry.getUser());
        } else throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "No entry with id " + entry.getId() + " in the database!",
                "");

        entryService.save(entryToUpdate);
        return Response.ok(EntryDataAssembler.toEntryDto(entryService.findById(entry.getId()))).build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete entry with specified id.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Entry deleted succesfully."),
            @ApiResponse(code = 404, message = "Entry with specified id not found in database."),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
    public Response deleteEntry (@PathParam("id") final String id) throws URISyntaxException, AppException {

        if(entryService.findById(id) == null) throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "Engine with specified id " + id + " doesn't exist in the database!",
                "Verify the existence of entry with id " + id + " in the database");

        entryService.delete(id);

        return Response.noContent().build();
    }
}