const calendar = document.querySelector(".calendar");
const monthYear = document.getElementById("monthYear");
const daysContainer = document.getElementById("days");
const eventForm = document.getElementById("eventForm");
const eventDetails = document.getElementById("eventDetails");
const eventStartInput = document.getElementById("eventStart");
const eventEndInput = document.getElementById("eventEnd");
const eventTextInput = document.getElementById("eventText");
const eventInstitutionInput = document.getElementById("eventInstitution"); // 새로운 입력 필드
const eventColorInput = document.getElementById("eventColor");
const saveEventButton = document.getElementById("saveEvent");
const closeFormButton = document.getElementById("closeForm");
const eventInfo = document.getElementById("eventInfo");
const editEventButton = document.getElementById("editEvent");
const deleteEventButton = document.getElementById("deleteEvent");
const closeDetailsButton = document.getElementById("closeDetails");

let currentMonth = 4; // May (0-based index)
let currentYear = 2024;
let events = [];
let selectedEvent = null;

function renderCalendar(month, year) {
  daysContainer.innerHTML = "";
  const firstDayOfMonth = new Date(year, month, 1).getDay();
  const lastDateOfMonth = new Date(year, month + 1, 0).getDate();
  const lastDayOfLastMonth =
    month === 0
      ? new Date(year - 1, 11, 0).getDate()
      : new Date(year, month, 0).getDate();

  monthYear.textContent = `${year}.${(month + 1).toString().padStart(2, "0")}`;

  // Days from previous month
  for (let i = firstDayOfMonth; i > 0; i--) {
    const day = document.createElement("div");
    day.classList.add("day", "prev-month");
    day.textContent = lastDayOfLastMonth - i + 1;
    daysContainer.appendChild(day);
  }

  // Days of current month
  for (let i = 1; i <= lastDateOfMonth; i++) {
    const day = document.createElement("div");
    day.classList.add("day");
    const date = `${year}-${(month + 1).toString().padStart(2, "0")}-${i
      .toString()
      .padStart(2, "0")}`;
    day.innerHTML = `<span>${i}</span>`;
    day.dataset.date = date;
    day.addEventListener("click", () => openEventForm(date));
    daysContainer.appendChild(day);
  }

  // Days of next month
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

  // Render events
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
        eventDiv.style.top = `${eventCount * 18}px`; // Adjust this value to decrease spacing
        eventDiv.style.height = `calc(100% / 5)`; // Adjust the height to fit within the day box
        eventDiv.style.marginTop = "2px";
        eventDiv.addEventListener("click", (e) => {
          e.stopPropagation();
          openEventDetails(event);
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
  eventInstitutionInput.value = ""; // 기관 입력 필드 초기화
  eventColorInput.value = "#ff0000";
  eventForm.style.display = "block";
  selectedEvent = null;
}

function closeEventForm() {
  eventForm.style.display = "none";
}

function saveEvent() {
  const startDate = eventStartInput.value;
  const endDate = eventEndInput.value;
  const text = eventTextInput.value;
  const institution = eventInstitutionInput.value; // 기관 정보 가져오기
  const color = eventColorInput.value;

  if (startDate && endDate && text) {
    if (selectedEvent) {
      selectedEvent.start = startDate;
      selectedEvent.end = endDate;
      selectedEvent.text = text;
      selectedEvent.institution = institution; // 기관 정보 업데이트
      selectedEvent.color = color;
    } else {
      events.push({ start: startDate, end: endDate, text, institution, color });
    }
    renderCalendar(currentMonth, currentYear);
    closeEventForm();
  }
}

function openEventDetails(event) {
  selectedEvent = event;
  eventInfo.innerHTML = `
        <p><strong>시작 날짜:</strong> ${event.start}</p>
        <p><strong>종료 날짜:</strong> ${event.end}</p>
        <p><strong>내용:</strong> ${event.text}</p>
        <p><strong>기관:</strong> ${event.institution}</p> <!-- 기관 정보 표시 -->
    `;
  eventDetails.style.display = "block";
}

function closeEventDetails() {
  eventDetails.style.display = "none";
}

function deleteEvent() {
  if (selectedEvent) {
    events = events.filter((event) => event !== selectedEvent);
    renderCalendar(currentMonth, currentYear);
    closeEventDetails();
  }
}

function editEvent() {
  if (selectedEvent) {
    eventStartInput.value = selectedEvent.start;
    eventEndInput.value = selectedEvent.end;
    eventTextInput.value = selectedEvent.text;
    eventInstitutionInput.value = selectedEvent.institution; // 기관 정보 입력
    eventColorInput.value = selectedEvent.color;
    eventForm.style.display = "block";
    closeEventDetails();
  }
}

document.getElementById("prev").addEventListener("click", () => {
  if (currentMonth === 0) {
    currentMonth = 11;
    currentYear--;
  } else {
    currentMonth--;
  }
  renderCalendar(currentMonth, currentYear);
});

document.getElementById("next").addEventListener("click", () => {
  if (currentMonth === 11) {
    currentMonth = 0;
    currentYear++;
  } else {
    currentMonth++;
  }
  renderCalendar(currentMonth, currentYear);
});

saveEventButton.addEventListener("click", saveEvent);
closeFormButton.addEventListener("click", closeEventForm);
editEventButton.addEventListener("click", editEvent);
deleteEventButton.addEventListener("click", deleteEvent);
closeDetailsButton.addEventListener("click", closeEventDetails);

renderCalendar(currentMonth, currentYear);
