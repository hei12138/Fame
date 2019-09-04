const isProd = process.env.NODE_ENV === 'production'

const devUrl = 'http://127.0.0.1:9090'
const devProxy = {
  host: '127.0.0.1',
  port: 9090
}

const prodUrl = 'http://127.0.0.1:80'
const prodProxy = {
  protocol: 'http',
  host: '127.0.0.1',
  port: 80
}

const config = {
  isProd: isProd,
  api: isProd ? prodUrl : devUrl,
  baseProxy: isProd ? prodProxy : devProxy
}

export default config
