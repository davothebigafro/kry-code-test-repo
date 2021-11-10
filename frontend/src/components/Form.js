import React from "react";
import { useForm } from "react-hook-form";
import "../form.css";

function Form(props) {
  // https://react-hook-form.com/api/useform
  // useForm is custom hook for managing forms with ease. It take
  const { register, handleSubmit, reset } = useForm();

  // Function to add a new service.
  // Takes in 'data' which is a name and url.
  // posts to the addservice endpoint
  const onSubmit = (data) => {
    fetch("http://localhost:8080/addservice", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    })
      .then((res) => {
        console.log("post response ", res);
      })
      .catch((err) => {
        console.log("error:", err);
      });
    reset();
    // Do the get request afterwards to update the page.
    props.handleGet();
  };

  // Url validation function
  const validateUrl = (str) => {
    let url;
    try {
      url = new URL(str);
    } catch (_) {
      return false;
    }
    return url.protocol === "http:" || url.protocol === "https:";
  };

  // Render the form
  // Input fields for name, url and a submit button.
  return (
    <div className="container">
      <h1>Add Service</h1>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className="row">
          <div className="col-25">
            <label>Name</label>
          </div>
          <div className="col-25">
            <input
              {...register("name", {
                required: true /*Can't submit if field is empty*/,
              })}
            />
          </div>
        </div>

        <div>
          <div className="col-25">
            <label>Url</label>
          </div>
          <div className="col-25">
            <input
              {...register("url", {
                required: true /*Can't submit if field is empty*/,
                validate: (value) =>
                  validateUrl(value) /*Check this is a valid url*/,
              })}
            />
          </div>
        </div>

        <div className="row">
          <input type="submit" />
        </div>
      </form>
    </div>
  );
}
export default Form;
