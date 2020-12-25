import React, { useState, useEffect } from "react";
import { Player } from "video-react";
import "video-react/dist/video-react.css";
import styles from "./VideoPlay.module.css";
import Header from "../Header/Header";
import { setCurrentUserIfLoggedIn } from "../Service";
import { VIDEO_SELECTED_ID } from "../Const";
import Avatar from "@material-ui/core/Avatar";
import Typography from "@material-ui/core/Typography";
import { makeStyles, createStyles } from "@material-ui/core/styles";

const useStyles = makeStyles(() =>
  createStyles({
    root: {
      height: "50px",
      width: "50px",
    },
  })
);

function VideoPlay() {
  const [videoInfo, setVideoInfo] = useState(undefined);
  const [currentUser, setCurrentUser] = useState(undefined);
  const classes = useStyles();

  useEffect(() => {
    setCurrentUserIfLoggedIn(setCurrentUser);
  }, []);

  function onLogoutEvent() {
    setCurrentUser(undefined);
  }

  if (!videoInfo)
    setVideoInfo(JSON.parse(localStorage.getItem(VIDEO_SELECTED_ID)));

  if (videoInfo !== undefined)
    return (
      <React.Fragment>
        <Header currentUser={currentUser} logoutEvent={onLogoutEvent} />
        <div className={styles.content_wrapper}>
          <div className={styles.title_wrapper}>
            <Avatar
              className={classes.root}
              alt=""
              src={
                videoInfo.userAvatarId !== undefined
                  ? "http://localhost:8080/api/user?av=" +
                    videoInfo.userAvatarId
                  : ""
              }
            />
            <div className={styles.names}>
              <div className={styles.videoname}>{videoInfo.videoname}</div>
              <div className={styles.username}>{videoInfo.username}</div>
            </div>
          </div>
          <Player
            className={styles.player}
            playsInline
            src={
              videoInfo.videoId !== undefined
                ? "http://localhost:8080/api/videos/" +
                  videoInfo.videoId +
                  "?vd"
                : ""
            }
            alt=""
            fluid={false}
            width={800}
            height={450}
          ></Player>
          <div className={styles.desc_wrapper}>
            <Typography variant="h6"> Description</Typography>
            <Typography variant="body1">{videoInfo.description}</Typography>
          </div>
        </div>
      </React.Fragment>
    );
  else return null;
}

VideoPlay.propTypes = {};

VideoPlay.defaultProps = {};

export default VideoPlay;
