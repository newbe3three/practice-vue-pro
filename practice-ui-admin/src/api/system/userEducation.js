import request from '@/utils/request'

// 创建教育经历
export function createUserEducation(data) {
  return request({
    url: '/system/user-education/create',
    method: 'post',
    data: data
  })
}

// 更新教育经历
export function updateUserEducation(data) {
  return request({
    url: '/system/user-education/update',
    method: 'put',
    data: data
  })
}

// 删除教育经历
export function deleteUserEducation(id) {
  return request({
    url: '/system/user-education/delete?id=' + id,
    method: 'delete'
  })
}

// 获得教育经历
export function getUserEducation(id) {
  return request({
    url: '/system/user-education/get?id=' + id,
    method: 'get'
  })
}

// 获得教育经历分页
export function getUserEducationPage(query) {
  return request({
    url: '/system/user-education/page',
    method: 'get',
    params: query
  })
}

// 导出教育经历 Excel
export function exportUserEducationExcel(query) {
  return request({
    url: '/system/user-education/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
