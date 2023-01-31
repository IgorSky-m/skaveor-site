export default async function wrapApiCall(
  call = () => new Promise(),
  apply = (result) => {},
  unauthorized = (result) => {},
  forbidden = (result) => {},
  other = (status, result) => {},
  error = (err) => console.error(err)
) {
  let status;
  const result = await call()
    .then((resp) => {
      status = resp.status;
      return resp.json();
    })
    .catch(error);

  if (status === 200) {
    apply(result);
  } else if (status === 401) {
    unauthorized(result);
  } else if (status === 403) {
    forbidden(result);
  } else {
    other(status, result);
  }

  return result;
}
