import request from '@/utils/request'

// 创建实践申请
export function createPracticeApply(data) {
  return request({
    url: '/system/practice-apply/create',
    method: 'post',
    data: data
  })
}

// 更新实践申请
export function updatePracticeApply(data) {
  return request({
    url: '/system/practice-apply/update',
    method: 'put',
    data: data
  })
}

// 删除实践申请
export function deletePracticeApply(id) {
  return request({
    url: '/system/practice-apply/delete?id=' + id,
    method: 'delete'
  })
}

// 获得实践申请
export function getPracticeApply(id) {
  return request({
    url: '/system/practice-apply/get?id=' + id,
    method: 'get'
  })
}

// 获得实践申请分页
export function getPracticeApplyPage(query) {
  return request({
    url: '/system/practice-apply/page',
    method: 'get',
    params: query
  })
}

// 导出实践申请 Excel
export function exportPracticeApplyExcel(query) {
  return request({
    url: '/system/practice-apply/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
