import React, { Component } from "react";
import Header from "../Header/Header";
import Videolist from "./Videolist";
import "video-react/dist/video-react.css";
import { findVideoinfosByPage, setCurrentUserIfLoggedIn } from "../Service";
import { VIDEOINFOS, PAGE_SIZE } from "../Const";

class Home extends Component {
  state = {
    videoinfos: [],
    currentUser: undefined,
  };

  componentDidMount() {
    this.onPageSelectEvent(0);
    setCurrentUserIfLoggedIn((data) => {
      this.setState({ currentUser: data });
    });
  }

  onPageSelectEvent = (pageNum) => {
    findVideoinfosByPage(PAGE_SIZE, pageNum)
      .then((response) => response.json())
      .then((videoinfoarray) => {
        this.setState({ videoinfos: videoinfoarray });

        sessionStorage.setItem(VIDEOINFOS, JSON.stringify(videoinfoarray));
      });
  };

  onLogoutEvent = () => {
    this.setState({ currentUser: undefined });
  };

  render() {
    return (
      <React.Fragment>
        <Header
          logoutEvent={this.onLogoutEvent}
          currentUser={this.state.currentUser}
        />
        <Videolist
          pageSelectEvent={this.onPageSelectEvent}
          videoinfos={this.state.videoinfos}
        />
        <link rel="stylesheet" href="/css/video-react.css" />
      </React.Fragment>
    );
  }
}

export default Home;
