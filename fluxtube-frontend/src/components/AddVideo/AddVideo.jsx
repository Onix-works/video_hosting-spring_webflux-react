import React, { useState, useEffect } from "react";
import styles from "./AddVideo.module.css";
import Header from "../Header/Header";
import { TextField, Input, Button } from "@material-ui/core";
import { saveVideoInfo, setCurrentUserIfLoggedIn } from "../Service";
import CircularProgress from "@material-ui/core/CircularProgress";
import { withRouter } from "react-router-dom";
import { makeStyles, createStyles } from "@material-ui/core/styles";
import { useHistory } from "react-router-dom";

const useStyles = makeStyles(() =>
  createStyles({
    root: {
      margin: "10px",
      fontSize: "12px",
    },
  })
);

function AddVideo(props) {
  const history = useHistory();
  const classes = useStyles();
  const [] = useState(null);
  const [description, setDescription] = useState(undefined);
  const [videoname, setVideoname] = useState(undefined);
  const [video, setVideo] = useState(undefined);
  const [thumbnail, setThumbnail] = useState(undefined);
  const [isLoading, setIsLoading] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);
  const [videoEmpty, setVideoEmpty] = useState(false);
  const [thumbnailEmpty, setThumbnailEmpty] = useState(false);
  const [videonameEmpty, setVideonameEmpty] = useState(false);
  const [descriptionEmpty, setDescriptionEmpty] = useState(false);

  useEffect(() => {
    setCurrentUserIfLoggedIn(setCurrentUser);
  }, []);

  function handleDescriptionChange(event) {
    setDescription(event.target.value);
  }

  function handleVideonameChange(event) {
    setVideoname(event.target.value);
  }

  function onLogoutEvent() {
    props.history.push("/");
  }

  function submit() {
    if (currentUser !== undefined) {
      let formData = new FormData();
      if (video !== undefined) {
        let videoB = new Blob(video);
        formData.append("video", videoB, "video1");
        setVideoEmpty(false);
      } else {
        setVideoEmpty(true);
        return;
      }
      if (thumbnail !== undefined) {
        let ThumB = new Blob(thumbnail);
        formData.append("thumbnail", ThumB, "thumbnail1");
        setThumbnailEmpty(false);
      } else {
        setThumbnailEmpty(true);
        return;
      }
      if (description !== undefined) {
        formData.append("description", description);
        setDescriptionEmpty(false);
      } else {
        setDescriptionEmpty(true);
        return;
      }
      if (videoname !== undefined) {
        formData.append("videoname", videoname);
        setVideonameEmpty(false);
      } else {
        setVideonameEmpty(true);
        return;
      }

      formData.append("username", currentUser.username);
      setIsLoading(true);
      saveVideoInfo(formData)
        .then((response) => {
          if (response.ok) {
          }
        })
        .finally(() => {
          setIsLoading(false);
          history.push("/");
        });
    }
  }

  function handleThumbnailChange(e) {
    const files = e.target.files;
    setThumbnail(files);
  }

  function handleVideoChange(e) {
    const files = e.target.files;
    setVideo(files);
  }

  function showCircularProgress() {
    if (isLoading) return <CircularProgress />;
  }

  function errorMessage() {
    if (videoEmpty)
      return <label style={{ color: "red" }}>Please upload video</label>;
    if (thumbnailEmpty)
      return <label style={{ color: "red" }}>Please upload thumbnail</label>;
    if (videonameEmpty)
      return <label style={{ color: "red" }}>Please insert video name</label>;
    if (descriptionEmpty)
      return <label style={{ color: "red" }}>Please insert description</label>;
  }

  return (
    <React.Fragment>
      <Header currentUser={currentUser} logoutEvent={onLogoutEvent} />
      <div className={styles.wrapper}>
        <Input
          className={styles.videoname}
          onChange={handleVideonameChange}
          placeholder="Video name"
        />
        <TextField
          multiline
          rows={4}
          variant="outlined"
          onChange={handleDescriptionChange}
          placeholder="Description"
        />

        <div className="form-group">
          <Button
            className={classes.root}
            variant="contained"
            size="small"
            component="label"
          >
            Upload Video
            <input hidden type="file" onChange={handleVideoChange} />
          </Button>

          <Button
            className={classes.root}
            variant="contained"
            size="small"
            component="label"
          >
            Upload Thumbnail
            <input hidden type="file" onChange={handleThumbnailChange} />
          </Button>
        </div>
        <Button className={classes.root} onClick={submit} variant="contained">
          Save
        </Button>
        {errorMessage()}
        {showCircularProgress()}
      </div>
    </React.Fragment>
  );
}

AddVideo.propTypes = {};

AddVideo.defaultProps = {};
export default AddVideo;
