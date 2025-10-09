const PROXY_CONFIG = [
  {
    context: ['/users'],
    target: 'http://localhost:8081',
    secure: true,
    logLevel: 'debug'
  }
]

module.exports = PROXY_CONFIG;
