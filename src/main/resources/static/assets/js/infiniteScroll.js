/**
 * 
 */
/*
const $container = document.querySelector(".card-container")
let itemIndex = 3

// 무한 스크롤을 위한 옵저버
const lastObserver = new IntersectionObserver((entries) => {
  const lastItem = entries[0]
  if (!lastItem.isIntersecting) {
    return
  }
  // 2. 마지막 요소가 나타났다면, fetchItem 함수로 추가적인 아이템을 불러옵니다.
  else {
    fetchItem()
    // 3. 기존 마지막 요소의 인터섹션 감지를 해제하고, 새롭게 생성된 마지막 요소에의 인터섹션 여부를 감지합니다. 
    lastObserver.unobserve(lastItem.target)
    lastObserver.observe(document.querySelector('.card:last-child'))
  }
})

// 1. 처음 한번 마지막 요소의 인터섹션 여부를 감지합니다.
lastObserver.observe(document.querySelector('.card:last-child'))

// 요소 추가를 위한 함수
const fetchItem = () => {
  for (let index = itemIndex; index < itemIndex + 2; index++) {
    const newElement = document.createElement("div")
    $container.append(newElement)
  }
  itemIndex += 2
}
*/
