import React from "react";
import Service from "./Service";

// Main screen which renders all the services
function Main(props) {
  // Function to add a new service.

  // Deletes service with ID 'id'
  const deleteService = (id) => {
    fetch("http://localhost:8080/deleteservice/" + id, {
      method: "DELETE",
    })
      .then((res) => {
        console.log("post response ", res);
      })
      .catch((err) => {
        console.log("error:", err);
      });
    console.log(id);
    console.log("tried to delete " + id);
  };

  console.log(props.services);
  return (
    <main className="main col-2">
      <h1>Services</h1>
      <div className="row"></div>
      <div>
        {
          /*Render each Service in "services" list prop*/ props.services.map(
            (service) => (
              <Service
                name={service.name}
                url={service.url}
                status={service.status}
                handleDelete={deleteService}
              ></Service>
            )
          )
        }
      </div>
    </main>
  );
}

export default Main;
