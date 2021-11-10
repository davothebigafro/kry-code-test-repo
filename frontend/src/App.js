import "./App.css";
import Form from "./components/Form";
import Main from "./components/Main";
import { useState, useEffect } from "react";

function App() {
  const [serviceList, setServiceList] = useState([]);
  const [latestchange, setLatestChange] = useState("");

  // Get all the existing services and put them into service List
  const getServices = () => {
    fetch("http://localhost:8080/services", {
      method: "GET",
    })
      .then((res) => {
        console.log("get response", res);
        return res.json();
      })
      .then((data) => {
        setServiceList(data);
      })
      .catch((err) => {
        console.log("error:", err);
      });
  };

  // Get the latest change and display it
  const getLatestChange = () => {
    fetch("http://localhost:8080/latestchange", {
      method: "GET",
    })
      .then((res) => {
        console.log("get latest change", res);
        return res.text();
      })
      .then((data) => {
        setLatestChange(data);
      })
      .catch((err) => {
        console.log("error:", err);
      });
  };

  const getServicesAndLatestChange = () => {
    getServices();
    getLatestChange();
  };

  // Fetch the services and latest change periodically
  useEffect(() => {
    const interval = setInterval(getServicesAndLatestChange(), 10000);
    return () => clearInterval(interval);
  });

  // Render the form and the list of services
  return (
    <div>
      <Form handleGet={getServices}></Form>
      <div>
        <h2>Latest Change</h2>
        <h3>{latestchange}</h3>
      </div>
      <Main services={serviceList} handleGet={getServices}></Main>
    </div>
  );
}

export default App;
