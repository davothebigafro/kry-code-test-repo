import { render } from "@testing-library/react";
import Form from "./Components/Form";

it("renders without crashing", () => {
  const div = document.createElement("div");
  render(<Form />, div);
});
