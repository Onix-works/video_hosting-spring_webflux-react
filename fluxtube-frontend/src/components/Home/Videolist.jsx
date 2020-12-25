import React, { Component } from "react";
import Videoview from "./Videoview";
import GridList from "@material-ui/core/GridList";
import GridListTile from "@material-ui/core/GridListTile";
import styles from "./Videolist.module.css";
import Pagination from "@material-ui/lab/Pagination";
import { getPageCount } from "../Service";
import { PAGE_SIZE } from "../Const";

class Videolist extends Component {
  state = {
    videoinfos: [],
    page: 1,
    pageCount: undefined,
  };

  static getDerivedStateFromProps(props) {
    let videoinfos = [...props.videoinfos];
    return {
      videoinfos: videoinfos,
    };
  }

  componentDidMount() {
    this.setState({ videoinfos: this.props.videoinfos });
    getPageCount(PAGE_SIZE)
      .then((response) => response.json())
      .then((data) => {
        this.setState({ pageCount: data });
      });
  }

  handlePageChange = (event, newPage) => {
    this.setState({ page: newPage });
    this.props.pageSelectEvent(newPage - 1);
  };

  render() {
    return (
      <React.Fragment>
        <div className={styles.wrapper}>
          <GridList
            classes={{ root: styles.gridlist }}
            cellHeight={160}
            spacing={15}
            cols={4}
          >
            {this.state.videoinfos.map((videoinfo) => {
              return (
                <GridListTile>
                  <Videoview videoinfo={videoinfo} />
                </GridListTile>
              );
              // return <Videoview videoinfo={videoinfo} />;
            })}
          </GridList>
          <Pagination
            count={this.state.pageCount}
            page={this.state.page}
            onChange={this.handlePageChange}
            className={styles.pagination}
          />
        </div>
      </React.Fragment>
    );
  }
}

export default Videolist;
