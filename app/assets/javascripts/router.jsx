import { Router } from 'abyssa'

export default Router()
  .configure({
    enableLogs: true,
    notFound: 'home'
  })