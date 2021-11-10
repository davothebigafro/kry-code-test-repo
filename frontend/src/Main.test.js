import { render, screen } from "@testing-library/react";
import Main from "./Components/Main";

const servicesList = [
  { name: "Google", url: "https://www.google.com/", status: "OK" },
  { name: "Reddit", url: "https://reddit.com/", status: "OK" },
  { name: "Youtube", url: "https://www.youtube.com/", status: "OK" },
];

it("Renders the services", () => {
  const div = document.createElement("div");
  render(<Main services={servicesList} />, div);
  expect(screen.getByText("Google")).toBeInTheDocument();
  expect(screen.getByText("Reddit")).toBeInTheDocument();
  expect(screen.getByText("Youtube")).toBeInTheDocument();

  const Amazon = screen.queryByText("Amazon");
  expect(Amazon).toBeNull(); // it doesn't exist

  expect(screen.getByText("https://www.google.com/")).toBeInTheDocument();
  expect(screen.getByText("https://reddit.com/")).toBeInTheDocument();
  expect(screen.getByText("https://www.youtube.com/")).toBeInTheDocument();
});
