export const saveToken = (token) => localStorage.setItem("token", token);
export const getToken = () => localStorage.getItem("token");
export const getRole = () => localStorage.getItem("role");
export const getName = () => localStorage.getItem("name");

export const saveUser = ({ token, role, name }) => {
  if (token) localStorage.setItem("token", token);
  if (role) localStorage.setItem("role", role);
  if (name) localStorage.setItem("name", name);
};

export const clearUser = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  localStorage.removeItem("name");
};

export const isLoggedIn = () => !!getToken();