const request = (options) => {
  const headers = new Headers({
    "Content-Type": "application/json",
  });

  options = Object.assign({}, { headers }, { credentials: "include" }, options);
  return fetch(options.url, options);
};

const requestFile = (options) => {
  const headers = new Headers();
  options = Object.assign({}, { headers }, { credentials: "include" }, options);
  return fetch(options.url, options);
};

export function findAllVideoinfos() {
  return request({ url: "http://localhost:8080/api/videos/" });
}

export function findVideoinfosByPage(size, page) {
  return request({
    url: "http://localhost:8080/api/page?page=" + page + "&size=" + size,
  });
}

export function getPageCount(size) {
  return request({ url: "http://localhost:8080/api/page?size=" + size });
}

export function findVideoInfoById(id) {
  return request({ url: "http://localhost:8080/api/videos/" + id });
}

export function findVideoById(id) {
  return request({ url: "http://localhost:8080/api/videos/" + id + "?vd" });
}

export function findUserByName(name) {
  return request({ url: "http://localhost:8080/api/user/" + name });
}

export function findThumbnailById(id) {
  return request({
    url: "http://localhost:8080/api/videos/file/" + id + "?th",
  });
}

export function deleteById(id) {
  return request({
    url: "http://localhost:8080/api/videos/" + id,
    method: "delete",
  });
}

export function saveVideoInfo(formData) {
  return requestFile({
    url: "http://localhost:8080/api/videos/",
    method: "post",
    body: formData,
  });
}

export function checkNameAvailability(name) {
  return request({ url: "http://localhost:8080/api/user?ch=" + name });
}

export function saveUser(formData) {
  return requestFile({
    url: "http://localhost:8080/api/user",
    method: "post",
    body: formData,
  });
}

export function login(authRequest) {
  return request({
    url: "http://localhost:8080/api/login",
    method: "post",
    body: JSON.stringify(authRequest),
  });
}

export function logout() {
  return request({ url: "http://localhost:8080/api/logout", method: "get" });
}

export function isLoggedIn() {
  return request({ url: "http://localhost:8080/api/user", method: "get" });
}

export function setCurrentUserIfLoggedIn(setCurrentUser) {
  let loginFail = () => {
    setCurrentUser(undefined);
  };
  isLoggedIn().then((response) => {
    if (response.ok) {
      response.text().then((data) => {
        if (data) {
          let str = data.replace(/"/g, "");
          findUserByName(str)
            .then((data) => data.json())
            .then((data) => {
              setCurrentUser(data);
            });
        } else {
          loginFail();
        }
      });
    } else {
      loginFail();
    }
  });
}
