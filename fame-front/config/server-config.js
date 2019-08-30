const isProd = process.env.NODE_ENV === 'production'

const devUrl = 'http://0.0.0.0:9090'
const devProxy = {
  host: '0.0.0.0',
  port: 9090
}

const prodUrl = ''
const prodProxy = {
  protocol: 'http',
  host: 'localhost',
  port: 9090
}

const config = {
  isProd: isProd,
  api: isProd ? prodUrl : devUrl,
  baseProxy: isProd ? prodProxy : devProxy
}

export default config
