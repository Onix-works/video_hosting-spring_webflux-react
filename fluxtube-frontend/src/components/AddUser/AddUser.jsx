import React, { useState, useEffect } from "react";
import styles from "./AddUser.module.css";
import Header from "../Header/Header";
import { Input, Button } from "@material-ui/core";
import {
  saveUser,
  setCurrentUserIfLoggedIn,
  checkNameAvailability,
} from "../Service";
import { makeStyles, createStyles } from "@material-ui/core/styles";
import { withRouter } from "react-router-dom";
import { useHistory } from "react-router-dom";

const useStyles = makeStyles(() =>
  createStyles({
    root: {
      margin: "10px",
      fontSize: "12px",
    },
  })
);

function AddUser(props) {
  const history = useHistory();
  const classes = useStyles();
  const [username, setUsername] = useState(undefined);
  const [password, setPassword] = useState(undefined);
  const [avatar, setAvatar] = useState(undefined);
  const [currentUser, setCurrentUser] = useState(undefined);
  const [usernameEmpty, setUsernameEmpty] = useState(false);
  const [passwordEmpty, setPasswordempty] = useState(false);
  const [usernameTaken, setUsernameTaken] = useState(false);

  useEffect(() => {
    setCurrentUserIfLoggedIn(setCurrentUser);
  }, []);

  function onLogoutEvent() {
    setCurrentUser(undefined);
  }

  function handleUsernameChange(event) {
    setUsername(event.target.value);
  }

  function handlePasswordChange(event) {
    setPassword(event.target.value);
  }

  function submit() {
    let formData = new FormData();
    if (avatar !== undefined) {
      let avatarB = new Blob(avatar);
      formData.append("avatar", avatarB, "avatar1");
    }
    if (username !== undefined) {
      formData.append("username", username);
      setUsernameEmpty(false);
    } else {
      setUsernameEmpty(true);
      return;
    }
    if (password !== undefined) {
      formData.append("password", password);
      setPasswordempty(false);
    } else {
      setPasswordempty(true);
      return;
    }

    setUsernameTaken(false);
    checkNameAvailability(username)
      .then((response) => response.json())
      .then((apiresponse) => {
        if (apiresponse.success === true)
          saveUser(formData).then((response) => {
            if (response.ok) {
              history.push("/");
            }
          });
        else setUsernameTaken(true);
      });
  }

  function handleAvatarChange(e) {
    const files = e.target.files;
    setAvatar(files);
  }

  function errorMessage() {
    if (usernameEmpty)
      return <label style={{ color: "red" }}>Please insert username</label>;
    if (passwordEmpty)
      return <label style={{ color: "red" }}>Please insert password</label>;
    if (usernameTaken)
      return <label style={{ color: "red" }}>Username already taken</label>;
  }

  return (
    <React.Fragment>
      <Header currentUser={currentUser} logoutEvent={onLogoutEvent} />
      <div className={styles.wrapper}>
        <Input onChange={handleUsernameChange} placeholder="Username" />
        <Input onChange={handlePasswordChange} placeholder="Password" />

        <Button
          className={classes.root}
          variant="contained"
          size="small"
          component="label"
        >
          Avatar
          <input hidden type="file" onChange={handleAvatarChange} />
        </Button>

        <Button onClick={submit} variant="contained">
          Save
        </Button>
        {errorMessage()}
      </div>
    </React.Fragment>
  );
}

AddUser.propTypes = {};

AddUser.defaultProps = {};

export default AddUser;
