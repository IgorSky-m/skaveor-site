export default function wrap(call = () => new Promise()) {
  let successFunc = (result) => {};
  let unauthorizedFunc = (result) => {};
  let forbiddenFunc = (result) => {};
  let otherFunc = (status, result) => {};
  let errorFunc = (error) => console.error(error);

  const self = {
    success: (func) => {
      successFunc = func;
      return self;
    },
    unauthorized: (func) => {
      unauthorizedFunc = func;
      return self;
    },
    forbidden: (func) => {
      forbiddenFunc = func;
      return self;
    },
    other: (func) => {
      otherFunc = func;
      return self;
    },
    error: (func) => {
      errorFunc = func;
      return self;
    },
    excute: async () => {
      let status;
      const result = await call()
        .then((resp) => {
          status = resp.status;
          return resp.json();
        })
        .catch(errorFunc);

      if (status === 200) {
        successFunc(result);
      } else if (status === 401) {
        unauthorizedFunc(result);
      } else if (status === 403) {
        forbiddenFunc(result);
      } else {
        otherFunc(status, result);
      }
    },
  };

  return self;
}
