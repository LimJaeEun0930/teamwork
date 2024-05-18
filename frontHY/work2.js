const calendar = document.querySelector(".calendar");
const monthYear = document.getElementById("monthYear");
const daysContainer = document.getElementById("days");
const eventForm = document.getElementById("eventForm");
const eventStartInput = document.getElementById("eventStart");
const eventEndInput = document.getElementById("eventEnd");
const eventTextInput = document.getElementById("eventText");
const eventColorInput = document.getElementById("eventColor");
const saveEventButton = document.getElementById("saveEvent");
const closeFormButton = document.getElementById("closeForm");

let currentMonth = 4; // May (0-based index)
let currentYear = 2024;
let events = [];

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
        (day) =>
          day.textContent.trim() === d.getDate().toString() &&
          day.querySelector("span").textContent === d.getDate().toString()
      );
      if (dayDiv) {
        const eventDiv = document.createElement("div");
        eventDiv.textContent = event.text;
        eventDiv.style.backgroundColor = event.color;
        eventDiv.style.color = "#fff";
        eventDiv.style.padding = "2px 5px";
        eventDiv.style.borderRadius = "5px";
        eventDiv.style.marginTop = "5px";
        dayDiv.appendChild(eventDiv);
      }
    }
  });
}

function openEventForm(date) {
  eventStartInput.value = date;
  eventEndInput.value = date;
  eventTextInput.value = "";
  eventForm.style.display = "block";
}

function closeEventForm() {
  eventForm.style.display = "none";
}

function saveEvent() {
  const startDate = eventStartInput.value;
  const endDate = eventEndInput.value;
  const text = eventTextInput.value;
  const color = eventColorInput.value;

  if (startDate && endDate && text) {
    events.push({ start: startDate, end: endDate, text, color });
    renderCalendar(currentMonth, currentYear);
    closeEventForm();
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

renderCalendar(currentMonth, currentYear);
