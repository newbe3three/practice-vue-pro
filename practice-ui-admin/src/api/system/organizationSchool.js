import request from '@/utils/request'

// 创建教育院校
export function createOrganizationSchool(data) {
  return request({
    url: '/system/organization-school/create',
    method: 'post',
    data: data
  })
}

// 更新教育院校
export function updateOrganizationSchool(data) {
  return request({
    url: '/system/organization-school/update',
    method: 'put',
    data: data
  })
}

// 删除教育院校
export function deleteOrganizationSchool(id) {
  return request({
    url: '/system/organization-school/delete?id=' + id,
    method: 'delete'
  })
}

// 获得教育院校
export function getOrganizationSchool(id) {
  return request({
    url: '/system/organization-school/get?id=' + id,
    method: 'get'
  })
}

// 获得教育院校分页
export function getOrganizationSchoolPage(query) {
  return request({
    url: '/system/organization-school/page',
    method: 'get',
    params: query
  })
}

// 导出教育院校 Excel
export function exportOrganizationSchoolExcel(query) {
  return request({
    url: '/system/organization-school/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 服务约定
export function confirmSign(data) {
  return request({
    url: '/system/organization-school/update_sign',
    method: 'put',
    data: data
  })
}

// 停止服务
export function stopService(id) {
  return request({
    url: '/system/organization-school/stop_service?id=' + id,
    method: 'get',
  })
}
