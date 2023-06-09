import request from '@/utils/request'

// 创建社会企业
export function createOrganizationCompany(data) {
  return request({
    url: '/system/organization-company/create',
    method: 'post',
    data: data
  })
}

// 更新社会企业
export function updateOrganizationCompany(data) {
  return request({
    url: '/system/organization-company/update',
    method: 'put',
    data: data
  })
}

// 删除社会企业
export function deleteOrganizationCompany(id) {
  return request({
    url: '/system/organization-company/delete?id=' + id,
    method: 'delete'
  })
}

// 获得社会企业
export function getOrganizationCompany(id) {
  return request({
    url: '/system/organization-company/get?id=' + id,
    method: 'get'
  })
}

// 获得社会企业分页
export function getOrganizationCompanyPage(query) {
  return request({
    url: '/system/organization-company/page',
    method: 'get',
    params: query
  })
}

// 导出社会企业 Excel
export function exportOrganizationCompanyExcel(query) {
  return request({
    url: '/system/organization-company/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 服务约定
export function confirmSign(data) {
  return request({
    url: '/system/organization-company/update_sign',
    method: 'put',
    data: data
  })
}


// 停止服务
export function stopService(id) {
  return request({
    url: '/system/organization-company/stop_service?id=' + id,
    method: 'get',
  })
}

// 发送邀请
export function sendInvite(id) {
  return request({
    url: '/system/organization-company/invite?id=' + id,
    method: 'get',
  })
}

