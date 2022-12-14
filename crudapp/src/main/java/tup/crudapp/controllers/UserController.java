package tup.crudapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tup.crudapp.models.User;
import tup.crudapp.repositories.UserRepository;

@Controller // This means that this class is a Controller
// La URL que vaya en la anotación habrá que agregarla detrás del puerto :8080
// en todas las llamadas a esta aplicación.
// Por ejemplo @RequestMapping("/user") resultaría en lo siguiente:
// localhost:8080/anime.... y detrás de esto habría que agregar el
// resto de la URL.
// En este caso, no necesitamos nada, y queda simplemente localhost:8080
@RequestMapping("")
public class UserController {
    // This means to get the bean called userRepository
    // which is auto-generated by Spring, we will use it to handle the data
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @PostMapping("/delete/{id}") // Map ONLY POST Requests
    public @ResponseBody String deleteUserById(@PathVariable Long id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        userRepository.deleteById(id);
        return "Deleted";
    }

    @GetMapping("/all")
    public @ResponseBody String getAllUsers() {
        // This returns a JSON or XML with the users
        Iterable<User> iterable = userRepository.findAll();
        String resp = """
                <style>
                #users {
                  font-family: Arial, Helvetica, sans-serif;
                  border-collapse: collapse;
                  width: 100%;
                }

                #users td, #users th {
                  border: 1px solid #ddd;
                  padding: 8px;
                }

                #users tr:nth-child(even){background-color: #f2f2f2;}

                #users tr:hover {background-color: #ddd;}

                #users th {
                  padding-top: 12px;
                  padding-bottom: 12px;
                  text-align: left;
                  background-color: #04AA6D;
                  color: white;
                }
                </style>
                """;
        resp += "<table id ='users'>"
                + "<tr>"
                + "<th>Id</th>"
                + "<th>Name</th>"
                + "<th>Email</th>"
                + "</tr>";
        // No puedo usar forEach() con una función lambda
        // porque el scope de las variables no lo permite.
        for (User user : iterable) {
            resp += "<tr>"
                    + "<td>" + user.getId() + "</td>"
                    + "<td>" + user.getName() + "</td>"
                    + "<td>" + user.getEmail() + "</td>"
                    + "</tr>";
        }
        return resp + "</table>";
    }

    @GetMapping("")
    public @ResponseBody String hola() {
        return "Hola";
    }
}

