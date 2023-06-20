import request from '@/utils/request'

// 创建实践
export function createPractice(data) {
  return request({
    url: '/system/practice/create',
    method: 'post',
    data: data
  })
}

// 更新实践
export function updatePractice(data) {
  return request({
    url: '/system/practice/update',
    method: 'put',
    data: data
  })
}

// 删除实践
export function deletePractice(id) {
  return request({
    url: '/system/practice/delete?id=' + id,
    method: 'delete'
  })
}

// 获得实践
export function getPractice(id) {
  return request({
    url: '/system/practice/get?id=' + id,
    method: 'get'
  })
}

// 获得实践分页
export function getPracticePage(query) {
  return request({
    url: '/system/practice/page',
    method: 'get',
    params: query
  })
}

// 导出实践 Excel
export function exportPracticeExcel(query) {
  return request({
    url: '/system/practice/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
