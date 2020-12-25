import React, { Component } from "react";
import VideoInfo from "../../models/VideoInfo";
import { withRouter } from "react-router-dom";
import { VIDEO_SELECTED_ID } from "../Const";
import styles from "./Videoview.module.css";
import Avatar from "@material-ui/core/Avatar";

class Videoview extends Component {
  state = {
    videoinfo: VideoInfo,
  };

  constructor(props) {
    super(props);
    this.selectVideo = this.selectVideo.bind(this);
  }

  static getDerivedStateFromProps(props, state) {
    let videoinfo = Object.assign({}, props.videoinfo);
    return {
      videoinfo: videoinfo,
    };
  }

  componentDidMount() {
    this.setState({ videoinfo: this.props.videoinfo });
  }

  selectVideo() {
    this.props.history.push("/videoplay");
    localStorage.setItem(
      VIDEO_SELECTED_ID,
      JSON.stringify(this.state.videoinfo)
    );
  }

  render() {
    return (
      <React.Fragment>
        <Avatar
          className={styles.avatar}
          alt=""
          src={
            this.props.videoinfo !== undefined
              ? "http://localhost:8080/api/user?av=" +
                this.state.videoinfo.userAvatarId
              : ""
          }
        />
        <div className={styles.names}>
          <div className={styles.videoname}>
            {this.props.videoinfo.videoname}
          </div>
          <div className={styles.username}>{this.props.videoinfo.username}</div>
        </div>
        <img
          className={styles.thumbnail}
          src={
            this.props.videoinfo !== undefined
              ? "http://localhost:8080/api/videos/" +
                this.state.videoinfo.thumbnailId +
                "?th"
              : ""
          }
          alt=""
          onClick={this.selectVideo}
        />
      </React.Fragment>
    );
  }
}
export default withRouter(Videoview);
