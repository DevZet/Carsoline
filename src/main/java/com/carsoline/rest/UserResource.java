package com.carsoline.rest;

import com.carsoline.rest.daos.User;
import com.carsoline.rest.dataAssemblers.EntryDataAssembler;
import com.carsoline.rest.dataAssemblers.UserDataAssembler;
import com.carsoline.rest.dtos.EntryDto;
import com.carsoline.rest.dtos.UserDto;
import com.carsoline.rest.exceptions.AppException;
import com.carsoline.services.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
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

@Path("/users")
@Api(value = "/users", description = "Operations on users", authorizations = {@Authorization(value="basicAuth")})
public class UserResource {

    private UserService userService;

    @Autowired
    private UserResource(UserService userService) { this.userService = userService; }

    @RolesAllowed( {"USER", "ADMIN"} )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get users collection",
            response = UserDto.class,
            responseContainer = "LIST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation succesful",
                    response = UserDto.class,
                    responseContainer = "LIST"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getUsers(){

        GenericEntity<Collection<UserDto>> userCollection =
                    new GenericEntity<Collection<UserDto>>(
                            UserDataAssembler.toUserDtoCollection(
                                    userService.getUsers())){};

        return Response.ok(userCollection).cacheControl(CacheControl.valueOf("30")).build();
    }

    @RolesAllowed( {"USER", "ADMIN"} )
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get user by id", response = UserDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation succesful", response = UserDto.class),
            @ApiResponse(code = 404, message = "User with specified id doesn't exist in database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getUserById(@PathParam("id") final String id) throws AppException {
        User user = userService.findOneById(id);

        if (user == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "User with specified id " + id + " doesn't exist in the database!",
                    "Verify the existence of the user with the id " + id + " in the database");
        }

        UserDto userDto = UserDataAssembler.toUserDto(userService.findOneById(id));

        return Response.ok(userDto).build();
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
    public Response getEntriesByUserId(@PathParam("id") final String id) throws AppException{
        User user = userService.findOneById(id);

        if(user == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "User with specified id " + id + " doesn't exist in the database!",
                    "Verify the existence of the user with the id " + id + " in the database");
        }

        GenericEntity<Collection<EntryDto>> entryCollection = new GenericEntity<Collection<EntryDto>>(
                EntryDataAssembler.toEntryDtoCollection(
                        user.getEntries())){};

        return Response.ok(entryCollection).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create new user",
            response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created succesfully", response = User.class),
            @ApiResponse(code = 409, message = "User with specified email already exists in database"),
            @ApiResponse(code = 409, message = "Password must not be null"),
            @ApiResponse(code = 409, message = "Email must not be null"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response addNewUser (User user) throws URISyntaxException, AppException {

        user.setId(UUID.randomUUID().toString());
        user.setRole("USER");

        if (user.getEnabled() == null) user.setEnabled((short)1);
        if (user.getEmail() == null) throw new AppException(Response.Status.CONFLICT.getStatusCode(), "Email must not be null", "Please specify your email.");
        if (user.getPassword() == null) throw new AppException(Response.Status.CONFLICT.getStatusCode(), "Password must not be null", "Please specify your password.");

        if(userService.findByEmail(user.getEmail()) == null) {
            userService.save(user);

            return Response.created(new URI("/users/"+user.getId()))
                    .entity(user)
                    .status(Response.Status.CREATED)
                    .build();
        } else throw new AppException(Response.Status.CONFLICT.getStatusCode(), "User with specified email " + user.getEmail() + " already exists in database." ,
                "Check existence of user with specified email in database.");

    }

    @RolesAllowed( {"USER", "ADMIN"} )
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update User with specified id", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated succesfully", response = User.class),
            @ApiResponse(code = 404, message = "Wrong credentials. Please check your email and password combination."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response updateUser (User userDto) throws URISyntaxException, AppException {
        User user = userService.findOneById(userDto.getId());

        if(userDto.getEnabled() == null) userDto.setEnabled((short)1);

        if (user == null) throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "User with specified id " + userDto.getId() + " doesn't exist in the database!",
                "Verify the existence of the user with the id " + userDto.getId() + " in the database");

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setEntries(userDto.getEntries());

        User updatedUser = userService.save(user);

        return Response.ok(UserDataAssembler.toUserDto(userService.findOneById(userDto.getId()))).build();
    }

    @RolesAllowed( {"USER", "ADMIN"} )
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete User with specified id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User deleted succesfully"),
            @ApiResponse(code = 404, message = "User with specified id doesn't exist in database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response deleteUser (@PathParam("id") final String id) throws URISyntaxException, AppException {

        if(userService.findOneById(id) == null) throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), "User with specified id " + id + " doesn't exist in the database!",
                "Verify the existence of user with id " + id + " in the database");

        userService.delete(id);

        return Response.noContent().build();
    }
}
