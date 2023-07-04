import request from '@/utils/request'

// 创建学生信息
export function createStudents(data) {
  return request({
    url: '/system/students/create',
    method: 'post',
    data: data
  })
}

// 更新学生信息
export function updateStudents(data) {
  return request({
    url: '/system/students/update',
    method: 'put',
    data: data
  })
}

// 删除学生信息
export function deleteStudents(id) {
  return request({
    url: '/system/students/delete?id=' + id,
    method: 'delete'
  })
}

// 获得学生信息
export function getStudents(id) {
  return request({
    url: '/system/students/get?id=' + id,
    method: 'get'
  })
}

// 获得学生信息分页
export function getStudentsPage(query) {
  return request({
    url: '/system/students/page',
    method: 'get',
    params: query
  })
}

// 导出学生信息 Excel
export function exportStudentsExcel(query) {
  return request({
    url: '/system/students/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
