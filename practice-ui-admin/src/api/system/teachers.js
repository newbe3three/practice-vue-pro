import request from '@/utils/request'

// 创建导师信息
export function createTeachers(data) {
  return request({
    url: '/system/teachers/create',
    method: 'post',
    data: data
  })
}

// 更新导师信息
export function updateTeachers(data) {
  return request({
    url: '/system/teachers/update',
    method: 'put',
    data: data
  })
}

// 删除导师信息
export function deleteTeachers(id) {
  return request({
    url: '/system/teachers/delete?id=' + id,
    method: 'delete'
  })
}

// 获得导师信息
export function getTeachers(id) {
  return request({
    url: '/system/teachers/get?id=' + id,
    method: 'get'
  })
}

// 获得导师信息分页
export function getTeachersPage(query) {
  return request({
    url: '/system/teachers/page',
    method: 'get',
    params: query
  })
}

// 导出导师信息 Excel
export function exportTeachersExcel(query) {
  return request({
    url: '/system/teachers/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
