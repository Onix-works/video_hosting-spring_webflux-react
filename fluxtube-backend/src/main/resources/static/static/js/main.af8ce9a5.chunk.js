(this.webpackJsonpfluxapp=this.webpackJsonpfluxapp||[]).push([[0],{124:function(e,t,a){e.exports={wrapper:"Login_wrapper__2lZ57"}},128:function(e,t,a){e.exports={wrapper:"AddUser_wrapper__241dK"}},146:function(e,t,a){},172:function(e,t,a){"use strict";a.r(t);var n=a(2),s=a(0),i=a.n(s),o=a(15),r=a.n(o),c=a(129),l=a(130),d=a(60),u=(a(146),function(e){e&&e instanceof Function&&a.e(3).then(a.bind(null,225)).then((function(t){var a=t.getCLS,n=t.getFID,s=t.getFCP,i=t.getLCP,o=t.getTTFB;a(e),n(e),s(e),i(e),o(e)}))}),j=a(27),h=a(33),p=a(35),b=a(34),v=a(25),O=a(212),f=function(e){var t=new Headers({"Content-Type":"application/json"});return e=Object.assign({},{headers:t},{credentials:"include"},e),fetch(e.url,e)},m=function(e){var t=new Headers;return e=Object.assign({},{headers:t},{credentials:"include"},e),fetch(e.url,e)};function g(e,t){return f({url:"http://localhost:8080/api/page?page="+t+"&size="+e})}function x(e){var t=function(){e(void 0)};f({url:"http://localhost:8080/api/user",method:"get"}).then((function(a){a.ok?a.text().then((function(a){if(a){var n=a.replace(/"/g,"");(s=n,f({url:"http://localhost:8080/api/user/"+s})).then((function(e){return e.json()})).then((function(t){e(t)}))}else t();var s})):t()}))}var _=a(14),y=a(47),w=a.n(y),S=a(213),k=a(214),C=a(210),P=a(223),N=a(222),V=Object(N.a)(C.a)({marginLeft:"10px",marginTop:"3px"}),U=Object(N.a)(P.a)({alignSelf:"center"}),B=Object(N.a)(O.a)({marginLeft:"20px",color:"white"}),F=Object(N.a)(B)({position:"absolute",right:"110px"}),E=Object(N.a)(B)({position:"absolute",right:"20px"}),L=function(e){Object(p.a)(a,e);var t=Object(b.a)(a);function a(e){var n;return Object(j.a)(this,a),(n=t.call(this,e)).state={},n.logoutButton=function(){f({url:"http://localhost:8080/api/logout",method:"get"}).then((function(e){e.ok&&n.props.logoutEvent()}))},n.state={redirect:!1,currentUser:void 0},n.loginButton=n.loginButton.bind(Object(v.a)(n)),n.homeButton=n.homeButton.bind(Object(v.a)(n)),n.addViButton=n.addViButton.bind(Object(v.a)(n)),n.addUsButton=n.addUsButton.bind(Object(v.a)(n)),n}return Object(h.a)(a,[{key:"loginButton",value:function(){this.props.history.push("/login")}},{key:"homeButton",value:function(){this.props.history.push("/")}},{key:"addViButton",value:function(){this.props.history.push("/addvideo")}},{key:"addUsButton",value:function(){this.props.history.push("/adduser")}},{key:"render",value:function(){return Object(n.jsx)(i.a.Fragment,{children:Object(n.jsx)(S.a,{position:"static",children:Object(n.jsxs)(k.a,{children:[Object(n.jsx)(C.a,{variant:"h4",onClick:this.homeButton,className:w.a.home_title,children:"FluxTube"}),void 0!==this.state.currentUser&&Object(n.jsxs)("div",{className:w.a.username_wrapper,children:[Object(n.jsx)(U,{alt:"",src:void 0!==this.state.currentUser.avatarId?"http://localhost:8080/api/user?av="+this.state.currentUser.avatarId:""}),Object(n.jsx)(V,{variant:"h6",children:this.state.currentUser.username}),Object(n.jsx)(B,{onClick:this.logoutButton,variant:"text",classes:{label:w.a.button},children:"Logout"})]}),void 0!==this.state.currentUser&&Object(n.jsx)(B,{onClick:this.addViButton,variant:"text",classes:{label:w.a.button},children:"Upload Video"}),void 0===this.state.currentUser&&Object(n.jsx)(F,{onClick:this.loginButton,variant:"text",classes:{label:w.a.button},children:"Login"}),void 0===this.state.currentUser&&Object(n.jsx)(E,{onClick:this.addUsButton,variant:"text",classes:{label:w.a.button},children:"Register"})]})})})}}],[{key:"getDerivedStateFromProps",value:function(e){return{currentUser:e.currentUser}}}]),a}(s.Component),I=Object(_.g)(L),D=a(131),A=function e(){Object(j.a)(this,e)},R="video_infos",T="video_selected_id",z=a(59),H=a.n(z),J=function(e){Object(p.a)(a,e);var t=Object(b.a)(a);function a(e){var n;return Object(j.a)(this,a),(n=t.call(this,e)).state={videoinfo:A},n.selectVideo=n.selectVideo.bind(Object(v.a)(n)),n}return Object(h.a)(a,[{key:"componentDidMount",value:function(){this.setState({videoinfo:this.props.videoinfo})}},{key:"selectVideo",value:function(){this.props.history.push("/videoplay"),localStorage.setItem(T,JSON.stringify(this.state.videoinfo))}},{key:"render",value:function(){return Object(n.jsxs)(i.a.Fragment,{children:[Object(n.jsx)(P.a,{className:H.a.avatar,alt:"",src:void 0!==this.props.videoinfo?"http://localhost:8080/api/user?av="+this.state.videoinfo.userAvatarId:""}),Object(n.jsxs)("div",{className:H.a.names,children:[Object(n.jsx)("div",{className:H.a.videoname,children:this.props.videoinfo.videoname}),Object(n.jsx)("div",{className:H.a.username,children:this.props.videoinfo.username})]}),Object(n.jsx)("img",{className:H.a.thumbnail,src:void 0!==this.props.videoinfo?"http://localhost:8080/api/videos/"+this.state.videoinfo.thumbnailId+"?th":"",alt:"",onClick:this.selectVideo})]})}}],[{key:"getDerivedStateFromProps",value:function(e,t){return{videoinfo:Object.assign({},e.videoinfo)}}}]),a}(s.Component),M=Object(_.g)(J),K=a(215),X=a(216),G=a(74),Y=a.n(G),Z=a(221),W=function(e){Object(p.a)(a,e);var t=Object(b.a)(a);function a(){var e;Object(j.a)(this,a);for(var n=arguments.length,s=new Array(n),i=0;i<n;i++)s[i]=arguments[i];return(e=t.call.apply(t,[this].concat(s))).state={videoinfos:[],page:1,pageCount:void 0},e.handlePageChange=function(t,a){e.setState({page:a}),e.props.pageSelectEvent(a-1)},e}return Object(h.a)(a,[{key:"componentDidMount",value:function(){var e,t=this;this.setState({videoinfos:this.props.videoinfos}),(e=8,f({url:"http://localhost:8080/api/page?size="+e})).then((function(e){return e.json()})).then((function(e){t.setState({pageCount:e})}))}},{key:"render",value:function(){return Object(n.jsx)(i.a.Fragment,{children:Object(n.jsxs)("div",{className:Y.a.wrapper,children:[Object(n.jsx)(K.a,{classes:{root:Y.a.gridlist},cellHeight:160,spacing:15,cols:4,children:this.state.videoinfos.map((function(e){return Object(n.jsx)(X.a,{children:Object(n.jsx)(M,{videoinfo:e})})}))}),Object(n.jsx)(Z.a,{count:this.state.pageCount,page:this.state.page,onChange:this.handlePageChange,className:Y.a.pagination})]})})}}],[{key:"getDerivedStateFromProps",value:function(e){return{videoinfos:Object(D.a)(e.videoinfos)}}}]),a}(s.Component),q=(a(77),function(e){Object(p.a)(a,e);var t=Object(b.a)(a);function a(){var e;Object(j.a)(this,a);for(var n=arguments.length,s=new Array(n),i=0;i<n;i++)s[i]=arguments[i];return(e=t.call.apply(t,[this].concat(s))).state={videoinfos:[],currentUser:void 0},e.onPageSelectEvent=function(t){g(8,t).then((function(e){return e.json()})).then((function(t){e.setState({videoinfos:t}),sessionStorage.setItem(R,JSON.stringify(t))}))},e.onLogoutEvent=function(){e.setState({currentUser:void 0})},e}return Object(h.a)(a,[{key:"componentDidMount",value:function(){var e=this;this.onPageSelectEvent(0),x((function(t){e.setState({currentUser:t})}))}},{key:"render",value:function(){return Object(n.jsxs)(i.a.Fragment,{children:[Object(n.jsx)(I,{logoutEvent:this.onLogoutEvent,currentUser:this.state.currentUser}),Object(n.jsx)(W,{pageSelectEvent:this.onPageSelectEvent,videoinfos:this.state.videoinfos}),Object(n.jsx)("link",{rel:"stylesheet",href:"/css/video-react.css"})]})}}]),a}(s.Component)),Q=a(217),$=a(124),ee=a.n($),te=Object(N.a)(O.a)({margin:"20px"}),ae=function(e){Object(p.a)(a,e);var t=Object(b.a)(a);function a(e){var n;return Object(j.a)(this,a),(n=t.call(this,e)).state={value:Object},n.state={value:{username:String,password:String}},n.handleLoginChange=n.handleLoginChange.bind(Object(v.a)(n)),n.handlePasswordChange=n.handlePasswordChange.bind(Object(v.a)(n)),n.submit=n.submit.bind(Object(v.a)(n)),n}return Object(h.a)(a,[{key:"handleLoginChange",value:function(e){var t=Object.assign(this.state);t.value.username=e.target.value,this.setState(t)}},{key:"handlePasswordChange",value:function(e){var t=Object.assign(this.state);t.value.password=e.target.value,this.setState(t)}},{key:"submit",value:function(){var e,t=this;(e=this.state.value,f({url:"http://localhost:8080/api/login",method:"post",body:JSON.stringify(e)})).then((function(e){e.ok&&(localStorage.setItem("logged_in",!0),t.props.history.push("/"))}))}},{key:"render",value:function(){return Object(n.jsxs)(i.a.Fragment,{children:[Object(n.jsx)(I,{}),Object(n.jsxs)("div",{className:ee.a.wrapper,children:[Object(n.jsx)(Q.a,{onChange:this.handleLoginChange,placeholder:"Login"}),Object(n.jsx)(Q.a,{onChange:this.handlePasswordChange,placeholder:"Password"}),Object(n.jsx)(te,{onClick:this.submit,variant:"contained",children:"Login"})]})]})}}]),a}(s.Component),ne=Object(_.g)(ae),se=a(22),ie=a(125),oe=a(86),re=a.n(oe),ce=a(220),le=a(219),de=a(218),ue=a(224),je=Object(de.a)((function(){return Object(ue.a)({root:{margin:"10px",fontSize:"12px"}})}));function he(e){var t=Object(_.f)(),a=je(),o=Object(s.useState)(null),r=(Object(ie.a)(o),Object(s.useState)(void 0)),c=Object(se.a)(r,2),l=c[0],d=c[1],u=Object(s.useState)(void 0),j=Object(se.a)(u,2),h=j[0],p=j[1],b=Object(s.useState)(void 0),v=Object(se.a)(b,2),f=v[0],g=v[1],y=Object(s.useState)(void 0),w=Object(se.a)(y,2),S=w[0],k=w[1],C=Object(s.useState)(!1),P=Object(se.a)(C,2),N=P[0],V=P[1],U=Object(s.useState)(void 0),B=Object(se.a)(U,2),F=B[0],E=B[1],L=Object(s.useState)(!1),D=Object(se.a)(L,2),A=D[0],R=D[1],T=Object(s.useState)(!1),z=Object(se.a)(T,2),H=z[0],J=z[1],M=Object(s.useState)(!1),K=Object(se.a)(M,2),X=K[0],G=K[1],Y=Object(s.useState)(!1),Z=Object(se.a)(Y,2),W=Z[0],q=Z[1];return Object(s.useEffect)((function(){x(E)}),[]),Object(n.jsxs)(i.a.Fragment,{children:[Object(n.jsx)(I,{currentUser:F,logoutEvent:function(){e.history.push("/")}}),Object(n.jsxs)("div",{className:re.a.wrapper,children:[Object(n.jsx)(Q.a,{className:re.a.videoname,onChange:function(e){p(e.target.value)},placeholder:"Video name"}),Object(n.jsx)(ce.a,{multiline:!0,rows:4,variant:"outlined",onChange:function(e){d(e.target.value)},placeholder:"Description"}),Object(n.jsxs)("div",{className:"form-group",children:[Object(n.jsxs)(O.a,{className:a.root,variant:"contained",size:"small",component:"label",children:["Upload Video",Object(n.jsx)("input",{hidden:!0,type:"file",onChange:function(e){var t=e.target.files;g(t)}})]}),Object(n.jsxs)(O.a,{className:a.root,variant:"contained",size:"small",component:"label",children:["Upload Thumbnail",Object(n.jsx)("input",{hidden:!0,type:"file",onChange:function(e){var t=e.target.files;k(t)}})]})]}),Object(n.jsx)(O.a,{className:a.root,onClick:function(){if(void 0!==F){var e=new FormData;if(void 0===f)return void R(!0);var a=new Blob(f);if(e.append("video",a,"video1"),R(!1),void 0===S)return void J(!0);var n=new Blob(S);if(e.append("thumbnail",n,"thumbnail1"),J(!1),void 0===l)return void q(!0);if(e.append("description",l),q(!1),void 0===h)return void G(!0);e.append("videoname",h),G(!1),e.append("username",F.username),V(!0),function(e){return m({url:"http://localhost:8080/api/videos/",method:"post",body:e})}(e).then((function(e){e.ok})).finally((function(){V(!1),t.push("/")}))}},variant:"contained",children:"Save"}),A?Object(n.jsx)("label",{style:{color:"red"},children:"Please upload video"}):H?Object(n.jsx)("label",{style:{color:"red"},children:"Please upload thumbnail"}):X?Object(n.jsx)("label",{style:{color:"red"},children:"Please insert video name"}):W?Object(n.jsx)("label",{style:{color:"red"},children:"Please insert description"}):void 0,function(){if(N)return Object(n.jsx)(le.a,{})}()]})]})}he.defaultProps={};var pe=he,be=a(126),ve=a(43),Oe=a.n(ve),fe=Object(de.a)((function(){return Object(ue.a)({root:{height:"50px",width:"50px"}})}));function me(){var e=Object(s.useState)(void 0),t=Object(se.a)(e,2),a=t[0],o=t[1],r=Object(s.useState)(void 0),c=Object(se.a)(r,2),l=c[0],d=c[1],u=fe();return Object(s.useEffect)((function(){x(d)}),[]),a||o(JSON.parse(localStorage.getItem(T))),void 0!==a?Object(n.jsxs)(i.a.Fragment,{children:[Object(n.jsx)(I,{currentUser:l,logoutEvent:function(){d(void 0)}}),Object(n.jsxs)("div",{className:Oe.a.content_wrapper,children:[Object(n.jsxs)("div",{className:Oe.a.title_wrapper,children:[Object(n.jsx)(P.a,{className:u.root,alt:"",src:void 0!==a.userAvatarId?"http://localhost:8080/api/user?av="+a.userAvatarId:""}),Object(n.jsxs)("div",{className:Oe.a.names,children:[Object(n.jsx)("div",{className:Oe.a.videoname,children:a.videoname}),Object(n.jsx)("div",{className:Oe.a.username,children:a.username})]})]}),Object(n.jsx)(be.Player,{className:Oe.a.player,playsInline:!0,src:void 0!==a.videoId?"http://localhost:8080/api/videos/"+a.videoId+"?vd":"",alt:"",fluid:!1,width:800,height:450}),Object(n.jsxs)("div",{className:Oe.a.desc_wrapper,children:[Object(n.jsx)(C.a,{variant:"h6",children:" Description"}),Object(n.jsx)(C.a,{variant:"body1",children:a.description})]})]})]}):null}me.defaultProps={};var ge=me,xe=a(128),_e=a.n(xe),ye=Object(de.a)((function(){return Object(ue.a)({root:{margin:"10px",fontSize:"12px"}})}));function we(e){var t=Object(_.f)(),a=ye(),o=Object(s.useState)(void 0),r=Object(se.a)(o,2),c=r[0],l=r[1],d=Object(s.useState)(void 0),u=Object(se.a)(d,2),j=u[0],h=u[1],p=Object(s.useState)(void 0),b=Object(se.a)(p,2),v=b[0],g=b[1],y=Object(s.useState)(void 0),w=Object(se.a)(y,2),S=w[0],k=w[1],C=Object(s.useState)(!1),P=Object(se.a)(C,2),N=P[0],V=P[1],U=Object(s.useState)(!1),B=Object(se.a)(U,2),F=B[0],E=B[1],L=Object(s.useState)(!1),D=Object(se.a)(L,2),A=D[0],R=D[1];return Object(s.useEffect)((function(){x(k)}),[]),Object(n.jsxs)(i.a.Fragment,{children:[Object(n.jsx)(I,{currentUser:S,logoutEvent:function(){k(void 0)}}),Object(n.jsxs)("div",{className:_e.a.wrapper,children:[Object(n.jsx)(Q.a,{onChange:function(e){l(e.target.value)},placeholder:"Username"}),Object(n.jsx)(Q.a,{onChange:function(e){h(e.target.value)},placeholder:"Password"}),Object(n.jsxs)(O.a,{className:a.root,variant:"contained",size:"small",component:"label",children:["Avatar",Object(n.jsx)("input",{hidden:!0,type:"file",onChange:function(e){var t=e.target.files;g(t)}})]}),Object(n.jsx)(O.a,{onClick:function(){var e,a=new FormData;if(void 0!==v){var n=new Blob(v);a.append("avatar",n,"avatar1")}void 0!==c?(a.append("username",c),V(!1),void 0!==j?(a.append("password",j),E(!1),R(!1),(e=c,f({url:"http://localhost:8080/api/user?ch="+e})).then((function(e){return e.json()})).then((function(e){!0===e.success?function(e){return m({url:"http://localhost:8080/api/user",method:"post",body:e})}(a).then((function(e){e.ok&&t.push("/")})):R(!0)}))):E(!0)):V(!0)},variant:"contained",children:"Save"}),N?Object(n.jsx)("label",{style:{color:"red"},children:"Please insert username"}):F?Object(n.jsx)("label",{style:{color:"red"},children:"Please insert password"}):A?Object(n.jsx)("label",{style:{color:"red"},children:"Username already taken"}):void 0]})]})}we.defaultProps={};var Se=we;r.a.render(Object(n.jsx)(c.a,{children:Object(n.jsxs)(l.a,{children:[Object(n.jsx)(d.a,{exact:!0,path:"/",component:function(){return Object(n.jsxs)(i.a.Fragment,{children:[Object(n.jsx)(q,{}),Object(n.jsx)("link",{rel:"stylesheet",href:"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"})]})}}),Object(n.jsx)(d.a,{exact:!0,path:"/login",component:function(){return Object(n.jsxs)(i.a.Fragment,{children:[Object(n.jsx)(ne,{}),Object(n.jsx)("link",{rel:"stylesheet",href:"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"})]})}}),Object(n.jsx)(d.a,{exact:!0,path:"/addvideo",component:function(){return Object(n.jsxs)(i.a.Fragment,{children:[Object(n.jsx)(pe,{}),Object(n.jsx)("link",{rel:"stylesheet",href:"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"})]})}}),Object(n.jsx)(d.a,{exact:!0,path:"/adduser",component:function(){return Object(n.jsxs)(i.a.Fragment,{children:[Object(n.jsx)(Se,{}),Object(n.jsx)("link",{rel:"stylesheet",href:"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"})]})}}),Object(n.jsx)(d.a,{exact:!0,path:"/videoplay",component:function(){return Object(n.jsxs)(i.a.Fragment,{children:[Object(n.jsx)(ge,{}),Object(n.jsx)("link",{rel:"stylesheet",href:"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"})]})}})]})}),document.getElementById("root")),u()},43:function(e,t,a){e.exports={player:"VideoPlay_player__1bWwd",content_wrapper:"VideoPlay_content_wrapper__3MX3E",title_wrapper:"VideoPlay_title_wrapper__3jC3j",desc_wrapper:"VideoPlay_desc_wrapper__2Exl9",videoname:"VideoPlay_videoname__3Fue6",avatar:"VideoPlay_avatar__2X29H",names:"VideoPlay_names__2kFCR"}},47:function(e,t,a){e.exports={username_wrapper:"Header_username_wrapper__Hswpr",home_title:"Header_home_title__2YRGN"}},59:function(e,t,a){e.exports={thumbnail:"Videoview_thumbnail__3kLKP",videoname:"Videoview_videoname__BLZKw",username:"Videoview_username__2fi3T",avatar:"Videoview_avatar__3YXUM",names:"Videoview_names__1jpBR"}},74:function(e,t,a){e.exports={wrapper:"Videolist_wrapper__1VVP1",pagination:"Videolist_pagination__2GvDi"}},86:function(e,t,a){e.exports={wrapper:"AddVideo_wrapper__12TDr",root:"AddVideo_root__3iaTL",videoname:"AddVideo_videoname__23bRh"}}},[[172,1,2]]]);
//# sourceMappingURL=main.af8ce9a5.chunk.js.map