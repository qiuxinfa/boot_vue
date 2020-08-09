import axios from 'axios'
import { MessageBox,Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 10000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
     const res = response.data
     return res
     // debugger
    // if the custom code is not 20000, it is judged as an error.
    // if (res.code != 200) {
    //   // Message({
    //   //   message: res.message || 'Error',
    //   //   type: 'error',
    //   //   duration: 5 * 1000
    //   // })

    //   // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
    //   if (res.code == 401) {
    //     // to re-login
    //     MessageBox.confirm('登录信息已过期，你可以选择留在当前页面或重新登录', '登录过期', {
    //       confirmButtonText: '重新登录',
    //       cancelButtonText: '取消',
    //       type: 'warning'
    //     }).then(() => {
    //       store.dispatch('user/logout').then(() => {
    //         location.reload()
    //       })
    //     })
    //   }else{
    //     return Promise.reject(new Error(res.msg || 'Error'))
    //   }

    // } else {
    //   return res
    // }
  },
  error => {
    // debugger
    // console.log('err' + error) // for debug
    if (error.response && error.response.status == 401) {
      // to re-login
      MessageBox.confirm('登录信息已过期，你可以选择留在当前页面或重新登录', '登录过期', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('user/logout').then(() => {
         location.reload()  // 为了重新实例化vue-router对象 避免bug
        })
      })
    }else{
      Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(error)
    }
  }
)

export default service
