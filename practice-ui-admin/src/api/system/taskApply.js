import request from '@/utils/request'

// 创建任务申请
export function createTaskApply(data) {
  return request({
    url: '/system/task-apply/create',
    method: 'post',
    data: data
  })
}

// 更新任务申请
export function updateTaskApply(data) {
  return request({
    url: '/system/task-apply/update',
    method: 'put',
    data: data
  })
}

// 删除任务申请
export function deleteTaskApply(id) {
  return request({
    url: '/system/task-apply/delete?id=' + id,
    method: 'delete'
  })
}

// 获得任务申请
export function getTaskApply(id) {
  return request({
    url: '/system/task-apply/get?id=' + id,
    method: 'get'
  })
}

// 获得任务申请分页
export function getTaskApplyPage(query) {
  return request({
    url: '/system/task-apply/page',
    method: 'get',
    params: query
  })
}

// 导出任务申请 Excel
export function exportTaskApplyExcel(query) {
  return request({
    url: '/system/task-apply/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
