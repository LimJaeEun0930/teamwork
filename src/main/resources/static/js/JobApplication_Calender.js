const calendar = document.querySelector(".calendar");
const monthYear = document.getElementById("monthYear");
const daysContainer = document.getElementById("days");
const eventForm = document.getElementById("eventForm");
const eventDetails = document.getElementById("eventDetails");
const eventStartInput = document.getElementById("eventStart");
const eventEndInput = document.getElementById("eventEnd");
const eventTextInput = document.getElementById("eventText");
const eventInstitutionInput = document.getElementById("eventInstitution");
const eventColorInput = document.getElementById("eventColor");
const saveEventButton = document.getElementById("saveEvent");
const closeFormButton = document.getElementById("closeForm");
const eventInfo = document.getElementById("eventInfo");
const editEventButton = document.getElementById("editEvent");
const deleteEventButton = document.getElementById("deleteEvent");
const closeDetailsButton = document.getElementById("closeDetails");
const viewCalendarsButton = document.getElementById("viewCalendars");
const calendarSelect = document.getElementById("calendarSelect");

let currentMonth = 4; // 5월 (0부터 시작하는 인덱스)
let currentYear = 2024;
let myCalendarEvents = []; // 내 캘린더 일정 저장
let interestCalendarEvents = []; // 관심 캘린더 일정 저장
let selectedEvent = null; // 선택된 이벤트를 저장
let currentCalendar = "myCalendar"; // 현재 선택된 캘린더

function renderCalendar(month, year) {
  daysContainer.innerHTML = ""; // 이전 날짜들을 초기화
  const firstDayOfMonth = new Date(year, month, 1).getDay(); // 월의 첫 번째 날의 요일
  const lastDateOfMonth = new Date(year, month + 1, 0).getDate(); // 월의 마지막 날
  const lastDayOfLastMonth =
    month === 0
      ? new Date(year - 1, 11, 0).getDate() // 이전 해의 12월 마지막 날
      : new Date(year, month, 0).getDate(); // 현재 해의 이전 월의 마지막 날

  monthYear.textContent = `${year}.${(month + 1).toString().padStart(2, "0")}`;

  // 이전 달의 날짜들
  for (let i = firstDayOfMonth; i > 0; i--) {
    const day = document.createElement("div");
    day.classList.add("day", "prev-month");
    day.textContent = lastDayOfLastMonth - i + 1;
    daysContainer.appendChild(day);
  }

  // 현재 달의 날짜들
  for (let i = 1; i <= lastDateOfMonth; i++) {
    const day = document.createElement("div");
    day.classList.add("day");
    const date = `${year}-${(month + 1).toString().padStart(2, "0")}-${i
      .toString()
      .padStart(2, "0")}`;
    day.innerHTML = `<span>${i}</span>`;
    day.dataset.date = date;
    day.addEventListener("click", () => openEventForm(date)); // 클릭 시 이벤트 폼 열기
    daysContainer.appendChild(day);
  }

  // 다음 달의 날짜들
  const totalDays = daysContainer.children.length;
  if (totalDays < 42) {
    const daysToAdd = 42 - totalDays;
    for (let i = 1; i <= daysToAdd; i++) {
      const day = document.createElement("div");
      day.classList.add("day", "next-month");
      day.textContent = i;
      daysContainer.appendChild(day);
    }
  }

  // 이벤트 렌더링
  const events =
    currentCalendar === "myCalendar"
      ? myCalendarEvents
      : interestCalendarEvents;
  events.forEach((event) => {
    const start = new Date(event.start);
    const end = new Date(event.end);
    for (let d = start; d <= end; d.setDate(d.getDate() + 1)) {
      const dateStr = `${d.getFullYear()}-${(d.getMonth() + 1)
        .toString()
        .padStart(2, "0")}-${d.getDate().toString().padStart(2, "0")}`;
      const dayDiv = [...daysContainer.children].find(
        (day) => day.dataset.date === dateStr
      );
      if (dayDiv) {
        const eventDiv = document.createElement("div");
        eventDiv.textContent = event.text;
        eventDiv.style.backgroundColor = event.color;
        eventDiv.classList.add("event");
        const eventCount = dayDiv.querySelectorAll(".event").length;
        eventDiv.style.top = `${eventCount * 18}px`; // 일정 간의 간격 조정
        eventDiv.style.height = `calc(100% / 5)`; // 일정 박스 내에서 높이 조정
        eventDiv.style.marginTop = "2px";
        eventDiv.addEventListener("click", (e) => {
          e.stopPropagation(); // 클릭 이벤트 전파 방지
          openEventDetails(event); // 이벤트 세부사항 열기
        });
        dayDiv.appendChild(eventDiv);
      }
    }
  });
}

function openEventForm(date) {
  eventStartInput.value = date;
  eventEndInput.value = date;
  eventTextInput.value = "";
  eventInstitutionInput.value = "";
  eventColorInput.value = "#ff0000";
  eventForm.style.display = "block"; // 이벤트 폼 표시
  selectedEvent = null;
}

function closeEventForm() {
  eventForm.style.display = "none"; // 이벤트 폼 숨기기
}

function saveEvent() {
  const startDate = eventStartInput.value;
  const endDate = eventEndInput.value;
  const text = eventTextInput.value;
  const institution = eventInstitutionInput.value;
  const color = eventColorInput.value;

  if (startDate && endDate && text && institution) {
    if (selectedEvent) {
      // 선택된 이벤트 수정
      selectedEvent.start = startDate;
      selectedEvent.end = endDate;
      selectedEvent.text = text;
      selectedEvent.institution = institution;
      selectedEvent.color = color;
    } else {
      // 새로운 이벤트 추가
      const newEvent = {
        start: startDate,
        end: endDate,
        text,
        institution,
        color,
      };
      if (currentCalendar === "myCalendar") {
        myCalendarEvents.push(newEvent);
      } else {
        interestCalendarEvents.push(newEvent);
      }
    }
    renderCalendar(currentMonth, currentYear); // 캘린더 다시 렌더링
    closeEventForm(); // 이벤트 폼 닫기
  }
}

function openEventDetails(event) {
  selectedEvent = event;
  eventInfo.innerHTML = `
        <p><strong>시작 날짜:</strong> ${event.start}</p>
        <p><strong>종료 날짜:</strong> ${event.end}</p>
        <p><strong>내용:</strong> ${event.text}</p>
        <p><strong>기관:</strong> ${event.institution}</p>
    `;
  eventDetails.style.display = "block"; // 이벤트 세부사항 표시
}

function closeEventDetails() {
  eventDetails.style.display = "none"; // 이벤트 세부사항 숨기기
}

function deleteEvent() {
  if (selectedEvent) {
    if (currentCalendar === "myCalendar") {
      myCalendarEvents = myCalendarEvents.filter(
        (event) => event !== selectedEvent
      );
    } else {
      interestCalendarEvents = interestCalendarEvents.filter(
        (event) => event !== selectedEvent
      );
    }
    renderCalendar(currentMonth, currentYear); // 캘린더 다시 렌더링
    closeEventDetails(); // 이벤트 세부사항 닫기
  }
}

function editEvent() {
  if (selectedEvent) {
    eventStartInput.value = selectedEvent.start;
    eventEndInput.value = selectedEvent.end;
    eventTextInput.value = selectedEvent.text;
    eventInstitutionInput.value = selectedEvent.institution;
    eventColorInput.value = selectedEvent.color;
    eventForm.style.display = "block"; // 이벤트 폼 표시
    closeEventDetails(); // 이벤트 세부사항 닫기
  }
}

// 이전 달 버튼 클릭 이벤트 리스너
document.getElementById("prev").addEventListener("click", () => {
  if (currentMonth === 0) {
    currentMonth = 11;
    currentYear--;
  } else {
    currentMonth--;
  }
  renderCalendar(currentMonth, currentYear);
});

// 다음 달 버튼 클릭 이벤트 리스너
document.getElementById("next").addEventListener("click", () => {
  if (currentMonth === 11) {
    currentMonth = 0;
    currentYear++;
  } else {
    currentMonth++;
  }
  renderCalendar(currentMonth, currentYear);
});

// 이벤트 저장 버튼 클릭 이벤트 리스너
saveEventButton.addEventListener("click", saveEvent);
// 이벤트 폼 닫기 버튼 클릭 이벤트 리스너
closeFormButton.addEventListener("click", closeEventForm);
// 이벤트 수정 버튼 클릭 이벤트 리스너
editEventButton.addEventListener("click", editEvent);
// 이벤트 삭제 버튼 클릭 이벤트 리스너
deleteEventButton.addEventListener("click", deleteEvent);
// 이벤트 세부사항 닫기 버튼 클릭 이벤트 리스너
closeDetailsButton.addEventListener("click", closeEventDetails);

// 캘린더 보기 버튼 클릭 이벤트 리스너
viewCalendarsButton.addEventListener("click", () => {
  currentCalendar = calendarSelect.value;
  renderCalendar(currentMonth, currentYear);
});

// 초기 캘린더 렌더링
renderCalendar(currentMonth, currentYear);