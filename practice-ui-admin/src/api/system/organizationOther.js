import request from '@/utils/request'

// 创建其他组织
export function createOrganizationOther(data) {
  return request({
    url: '/system/organization-other/create',
    method: 'post',
    data: data
  })
}

// 更新其他组织
export function updateOrganizationOther(data) {
  return request({
    url: '/system/organization-other/update',
    method: 'put',
    data: data
  })
}

// 删除其他组织
export function deleteOrganizationOther(id) {
  return request({
    url: '/system/organization-other/delete?id=' + id,
    method: 'delete'
  })
}

// 获得其他组织
export function getOrganizationOther(id) {
  return request({
    url: '/system/organization-other/get?id=' + id,
    method: 'get'
  })
}

// 获得其他组织分页
export function getOrganizationOtherPage(query) {
  return request({
    url: '/system/organization-other/page',
    method: 'get',
    params: query
  })
}

// 导出其他组织 Excel
export function exportOrganizationOtherExcel(query) {
  return request({
    url: '/system/organization-other/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 停止服务
export function stopService(id) {
  return request({
    url: '/system/organization-other/stop_service?id=' + id,
    method: 'get',
  })
}
