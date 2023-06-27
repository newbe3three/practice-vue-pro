import request from '@/utils/request'

// 创建学校申请实践
export function createPracticeSchool(data) {
  return request({
    url: '/system/practice-school/create',
    method: 'post',
    data: data
  })
}

// 更新学校申请实践
export function updatePracticeSchool(data) {
  return request({
    url: '/system/practice-school/update',
    method: 'put',
    data: data
  })
}

// 删除学校申请实践
export function deletePracticeSchool(id) {
  return request({
    url: '/system/practice-school/delete?id=' + id,
    method: 'delete'
  })
}

// 获得学校申请实践
export function getPracticeSchool(id) {
  return request({
    url: '/system/practice-school/get?id=' + id,
    method: 'get'
  })
}

// 获得学校申请实践分页
export function getPracticeSchoolPage(query) {
  return request({
    url: '/system/practice-school/page',
    method: 'get',
    params: query
  })
}

// 导出学校申请实践 Excel
export function exportPracticeSchoolExcel(query) {
  return request({
    url: '/system/practice-school/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
