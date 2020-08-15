import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function getImgCode() {
  return request({
    url: '/auth/code',
    method: 'get'
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

export function getMenu(userId) {
  return request({
    url: '/auth/menu',
    method: 'get',
    params: { userId }
  })
}

export function getUserList(params) {
  return request({
    url: '/user/list',
    method: 'get',
    params
  })
}

export function addUser(data) {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}

export function deleteUser(id) {
  return request({
    url: '/user/delete',
    method: 'post',
    params: id
  })
}

export function updateUser(data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}

export function uploadImg(data) {
  return request({
    url: '/file/upload',
    method: 'post',
    data
  })
}

export function exportUser(params) {
  return request({
    url: '/user/export',
    method: 'get',
    params,
    responseType: 'blob'
    // responseType: 'arraybuffer'
  })
}

export function importUser(data) {
  return request({
    url: '/user/import',
    method: 'post',
    data,
    headers: {'Content-Type': 'multipart/form-data' }
  })
}
