import router from './router'
// import Layout from '@/layout'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

// 格式化后端传回来的路由信息
const formatRoutes = (routes) => {
  // debugger
  let fmtRoutes = []
  routes.forEach(route => {
    if (route.children) {
      route.children = formatRoutes(route.children)
    }
    let fmtRoute = {}
    if(route.type == 0){
      // 目录
      fmtRoute = {
        path: "/root"+route.url,
        component: resolve => {
          require(['@/layout/index.vue'], resolve)
        },
        name: "/root"+route.url,
        meta: { title: route.name, icon: route.icon },
        children: route.children
      }
    }else{
      // 菜单
      fmtRoute = {
        path: route.url,
        component: resolve => {
          require(['@/views/' + route.component + '.vue'], resolve)
        },
        name: route.url,
        meta: { title: route.name, icon: route.icon },
        children: route.children
      }
    }

    fmtRoutes.push(fmtRoute)
  })
  return fmtRoutes
}

// 添加公共布局组件Layout
const addCommonLayout = (routes) => {
  // debugger
  let fmtRoutes = []
  routes.forEach(route => {
    let fmtRoute = {
        path: '/common'+route.path,
        name: '/common'+route.path,
        component: resolve => {
          require(['@/layout/index.vue'], resolve)
        },
        redirect: route.path,
        meta: route.meta,
        children: [route]
      }
    fmtRoutes.push(fmtRoute)
  })
  return fmtRoutes
}

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      const hasGetUserInfo = store.getters.name
      const hasGetMenuList = store.getters.menuList
      // debugger
      if (hasGetMenuList) {
        next()
      } else {
        try {
          // get user info
          // await store.dispatch('user/getInfo')

          // get menu list
          // console.log("开启请求菜单")
          let re = await store.dispatch('user/getMenuList').then((ress) => {
            console.log("menu请求成功"+ress.data)
            // 格式化菜单
            let finalRoutes = formatRoutes(ress.data)
            // let finalRoutes = addCommonLayout(tmp)
            router.options.routes = finalRoutes
            router.addRoutes(finalRoutes)
            // debugger
            next({ ...to, replace: true })
            // next()
          }).catch((err) => {
           // console.log("menu请求失败")
          })

          // next()
          // next({ path: '/' })
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          // next(`/login`)
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      // next(`/login`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
