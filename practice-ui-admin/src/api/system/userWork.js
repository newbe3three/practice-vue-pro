import request from '@/utils/request'

// 创建工作经历
export function createUserWork(data) {
  return request({
    url: '/system/user-work/create',
    method: 'post',
    data: data
  })
}

// 更新工作经历
export function updateUserWork(data) {
  return request({
    url: '/system/user-work/update',
    method: 'put',
    data: data
  })
}

// 删除工作经历
export function deleteUserWork(id) {
  return request({
    url: '/system/user-work/delete?id=' + id,
    method: 'delete'
  })
}

// 获得工作经历
export function getUserWork(id) {
  return request({
    url: '/system/user-work/get?id=' + id,
    method: 'get'
  })
}

// 获得工作经历分页
export function getUserWorkPage(query) {
  return request({
    url: '/system/user-work/page',
    method: 'get',
    params: query
  })
}

// 导出工作经历 Excel
export function exportUserWorkExcel(query) {
  return request({
    url: '/system/user-work/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
