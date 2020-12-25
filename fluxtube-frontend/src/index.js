import React from "react";
import ReactDOM from "react-dom";
import BrowserRouter from "react-router-dom/es/BrowserRouter";
import Switch from "react-router-dom/es/Switch";
import Route from "react-router-dom/es/Route";
import "./index.css";
import reportWebVitals from "./reportWebVitals";
import Home from "./components/Home/Home";
import Login from "./components/Login/Login";
import AddVideo from "./components/AddVideo/AddVideo";
import VideoPlay from "./components/VideoPlay/VideoPlay";
import AddUser from "./components/AddUser/AddUser";

ReactDOM.render(
  <BrowserRouter>
    <Switch>
      <Route
        exact
        path="/"
        component={() => (
          <React.Fragment>
            <Home />
            <link
              rel="stylesheet"
              href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            />
          </React.Fragment>
        )}
      />
      <Route
        exact
        path="/login"
        component={() => (
          <React.Fragment>
            <Login />
            <link
              rel="stylesheet"
              href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            />
          </React.Fragment>
        )}
      />
      <Route
        exact
        path="/addvideo"
        component={() => (
          <React.Fragment>
            <AddVideo />
            <link
              rel="stylesheet"
              href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            />
          </React.Fragment>
        )}
      />
      <Route
        exact
        path="/adduser"
        component={() => (
          <React.Fragment>
            <AddUser />
            <link
              rel="stylesheet"
              href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            />
          </React.Fragment>
        )}
      />
      <Route
        exact
        path="/videoplay"
        component={() => (
          <React.Fragment>
            <VideoPlay />
            <link
              rel="stylesheet"
              href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            />
          </React.Fragment>
        )}
      />
    </Switch>
  </BrowserRouter>,
  document.getElementById("root")
);

reportWebVitals();
