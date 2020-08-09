import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/permission/list',
    method: 'get',
    params
  })
}

export function doAdd(data) {
  return request({
    url: '/permission/add',
    method: 'post',
    data
  })
}

export function doDelete(id) {
  return request({
    url: '/permission/delete',
    method: 'post',
    params: id
  })
}

export function doUpdate(data) {
  return request({
    url: '/permission/update',
    method: 'post',
    data
  })
}

export function getAllPermission(type) {
  return request({
    url: '/permission/getAllPermission',
    method: 'get',
    params: type
  })
}
