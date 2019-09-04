const isProd = process.env.NODE_ENV === "production";

const devUrl = "http://www.woolboy.cn/";
const prodUrl = "http://www.woolboy.cn/";
const devFrontUrl = "http://www.woolboy.cn/";
const prodFrontUrl = "/";

const api = isProd ? prodUrl : devUrl;
const frontUrl = isProd ? prodFrontUrl : devFrontUrl;
export default {
  isProd,
  api,
  frontUrl
};
