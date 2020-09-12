function callback(req) {
  const authToken = localStorage.getItem('sc-authenticationToken') || sessionStorage.getItem('sc-authenticationToken');
  if (authToken) {
    req.headers['Authorization'] = 'Bearer ' + authToken;
  }
  return req;
}

// noinspection JSUnresolvedVariable,JSDeprecatedSymbols
chrome.browser.webRequest.onBeforeSendHeaders.addListener(callback, { urls: ['*management/toggles-console*'] }, [
  'blocking',
  'requestHeaders',
]);
