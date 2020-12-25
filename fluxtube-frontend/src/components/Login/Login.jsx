import React, { Component } from "react";
import Header from "../Header/Header";
import "video-react/dist/video-react.css";
import { Input, Button } from "@material-ui/core";
import { login } from "../Service";
import { withRouter } from "react-router-dom";
import { LOGGED_IN } from "../Const";
import styles from "./Login.module.css";
import { styled } from "@material-ui/core/styles";

const MyButton = styled(Button)({
  margin: "20px",
});

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = { value: { username: String, password: String } };
    this.handleLoginChange = this.handleLoginChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.submit = this.submit.bind(this);
  }

  state = { value: Object };

  handleLoginChange(event) {
    let newstate = Object.assign(this.state);
    newstate.value.username = event.target.value;
    this.setState(newstate);
  }
  handlePasswordChange(event) {
    let newstate = Object.assign(this.state);
    newstate.value.password = event.target.value;
    this.setState(newstate);
  }

  submit() {
    login(this.state.value).then((response) => {
      if (response.ok) {
        localStorage.setItem(LOGGED_IN, true);
        this.props.history.push("/");
      }
    });
  }

  render() {
    return (
      <React.Fragment>
        <Header />
        <div className={styles.wrapper}>
          <Input onChange={this.handleLoginChange} placeholder="Login" />
          <Input onChange={this.handlePasswordChange} placeholder="Password" />

          <MyButton onClick={this.submit} variant="contained">
            Login
          </MyButton>
        </div>
      </React.Fragment>
    );
  }
}

export default withRouter(Login);
