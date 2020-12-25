import React, { Component } from "react";
import { Button } from "@material-ui/core";
import { logout } from "../Service";
import { withRouter } from "react-router-dom";
import styles from "./Header.module.css";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Avatar from "@material-ui/core/Avatar";
import { styled } from "@material-ui/core/styles";

const MyTypography = styled(Typography)({
  marginLeft: "10px",
  marginTop: "3px",
});

const MyAvatar = styled(Avatar)({
  alignSelf: "center",
});

const MyButton = styled(Button)({
  marginLeft: "20px",
  color: "white",
});

const LoginButton = styled(MyButton)({
  position: "absolute",
  right: "110px",
});

const RegisterButton = styled(MyButton)({
  position: "absolute",
  right: "20px",
});

class Header extends Component {
  state = {};

  constructor(props) {
    super(props);
    this.state = { redirect: false, currentUser: undefined };
    this.loginButton = this.loginButton.bind(this);
    this.homeButton = this.homeButton.bind(this);
    this.addViButton = this.addViButton.bind(this);
    this.addUsButton = this.addUsButton.bind(this);
  }

  static getDerivedStateFromProps(props) {
    let currentUser = props.currentUser;
    return {
      currentUser: currentUser,
    };
  }

  logoutButton = () => {
    logout().then((response) => {
      if (response.ok) {
        this.props.logoutEvent();
      }
    });
  };

  loginButton() {
    this.props.history.push("/login");
  }

  homeButton() {
    this.props.history.push("/");
  }

  addViButton() {
    this.props.history.push("/addvideo");
  }

  addUsButton() {
    this.props.history.push("/adduser");
  }

  render() {
    return (
      <React.Fragment>
        <AppBar position="static">
          <Toolbar>
            <Typography
              variant="h4"
              onClick={this.homeButton}
              className={styles.home_title}
            >
              FluxTube
            </Typography>
            {this.state.currentUser !== undefined && (
              <div className={styles.username_wrapper}>
                <MyAvatar
                  alt=""
                  src={
                    this.state.currentUser.avatarId !== undefined
                      ? "http://localhost:8080/api/user?av=" +
                        this.state.currentUser.avatarId
                      : ""
                  }
                />
                <MyTypography variant="h6">
                  {this.state.currentUser.username}
                </MyTypography>
                <MyButton
                  onClick={this.logoutButton}
                  variant="text"
                  classes={{ label: styles.button }}
                >
                  Logout
                </MyButton>
              </div>
            )}

            {this.state.currentUser !== undefined && (
              <MyButton
                onClick={this.addViButton}
                variant="text"
                classes={{ label: styles.button }}
              >
                Upload Video
              </MyButton>
            )}
            {this.state.currentUser === undefined && (
              <LoginButton
                onClick={this.loginButton}
                variant="text"
                classes={{ label: styles.button }}
              >
                Login
              </LoginButton>
            )}
            {this.state.currentUser === undefined && (
              <RegisterButton
                onClick={this.addUsButton}
                variant="text"
                classes={{ label: styles.button }}
              >
                Register
              </RegisterButton>
            )}
          </Toolbar>
        </AppBar>
      </React.Fragment>
    );
  }
}

export default withRouter(Header);
