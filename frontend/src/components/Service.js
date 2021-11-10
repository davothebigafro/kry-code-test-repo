import React, { useState } from "react";
import "../form.css";

// Renders an individual service
// with its name, url, status (OK, FAIL)
function Service(props) {
  const deleteMe = () => {
    props.handleDelete(props.name);
  };
  return (
    <div className="service">
      <h3 className="blocktext">{props.name}</h3>
      <a className="a.url" href={props.url} target="_blank">
        {props.url}
      </a>
      <div>Status: {props.status}</div>
      <button className="remove" onClick={deleteMe}>
        Delete
      </button>
    </div>
  );
}
export default Service;
