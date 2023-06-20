import request from '@/utils/request'

// 创建任务
export function createTask(data) {
  return request({
    url: '/system/task/create',
    method: 'post',
    data: data
  })
}

// 更新任务
export function updateTask(data) {
  return request({
    url: '/system/task/update',
    method: 'put',
    data: data
  })
}

// 删除任务
export function deleteTask(id) {
  return request({
    url: '/system/task/delete?id=' + id,
    method: 'delete'
  })
}

// 获得任务
export function getTask(id) {
  return request({
    url: '/system/task/get?id=' + id,
    method: 'get'
  })
}

// 获得任务分页
export function getTaskPage(query) {
  return request({
    url: '/system/task/page',
    method: 'get',
    params: query
  })
}

// 导出任务 Excel
export function exportTaskExcel(query) {
  return request({
    url: '/system/task/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 审核-任务通过
export function passTask(id) {
  return request({
    url: '/system/task/pass?id=' + id,
    method: 'get'
  })
}

// 审核-任务驳回
export function rejectTask(id, reason) {
  return request({
    url: '/system/task/reject?id=' + id + '&reason=' + reason,
    method: 'get'
  })
}

// 审核-任务通过
export function getApplyList(id) {
  return request({
    url: '/system/task/applyList?id=' + id,
    method: 'get'
  })
}
