import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/role/list',
    method: 'get',
    params
  })
}

export function doAdd(data) {
  return request({
    url: '/role/add',
    method: 'post',
    data
  })
}

export function doDelete(id) {
  return request({
    url: '/role/delete',
    method: 'post',
    params: id
  })
}

export function doUpdate(data) {
  return request({
    url: '/role/update',
    method: 'post',
    data
  })
}

export function getRoleList() {
  return request({
    url: '/role/getRoleList',
    method: 'get'
  })
}
