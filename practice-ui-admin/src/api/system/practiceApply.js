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

// 实践审核
// export function reviewPracticeApply(id) {
//   return request({
//     url: '/system/practice-apply/review?practiceApplyId=' + id,
//     method: 'get',
//   })
// }

// 驳回申请
export function reviewFailurePracticeApply(id, reason) {
  return request({
    url: '/system/practice-apply/review/failure',
    method: 'post',
    data: {
      'applyId': id,
      'suggestion': suggestion,
    }
  })
}

//////////////////////////////////////////////////////// 企业-实践申请管理（学生） //////////////////////////////////////////////////////////////
// 学生申请列表todo
export function companyGetPracticeApply(query) {
  return request({
    url: '/system/practice-apply/company/page',
    method: 'get',
    data: query,
  })
}
//实践申请审核提示
export function reviewPracticeApply(id) {
  return request({
    url: '/system/practice-apply/review?practiceApplyId=' + id,
    method: 'get',
  })
}

//通过申请
export function reviewPassPractice(id) {
  return request({
    url: '/system/practice-apply/review/pass?practiceApplyId=' + id,
    method: 'get',
  })
}

//实践申请审核未通过
export function reviewFailurePractice(id, suggestion) {
  return request({
    url: '/system/practice-apply/review/failure',
    method: 'post',
    data: {
      'applyId': id,
      'suggestion': suggestion,
    }
  })
}


//////////////////////////////////////////////////////// 企业-实践申请管理（学校） //////////////////////////////////////////////////////////////
//实践申请审核未通过
export function companyGetPracticeSchoolPage(query) {
  return request({
    url: '/system/practice-school/company/page',
    method: 'get',
    data: query,
  })
}

export function passPracticeSchool(id) {
  return request({
    url: '/system/practice-school/company/review/pass?practiceSchoolId=' + id,
    method: 'get',
  })
}




//////////////////////////////////////////////////////// 企业-实践申请管理（学生） //////////////////////////////////////////////////////////////


//院校端查询本校发起的对实践的申请
export function schoolGetPracticeSchoolPage(query) {
  return request({
    url: '/system/practice-school/school/page',
    method: 'get',
    data: query,
  })
}






