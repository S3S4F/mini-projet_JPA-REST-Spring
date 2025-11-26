const API_URL = "http://localhost:8080/api/auth";

const register = async (username, email, password) => {
  console.log("Registering user:", { username, email });
  try {
    const response = await fetch(`${API_URL}/signup`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, email, password }),
    });

    const data = await response.json();
    console.log("Register response:", data);
    
    if (!response.ok) {
      throw new Error(data.message || "Registration failed");
    }
    
    return data;
  } catch (error) {
    console.error("Registration error:", error);
    throw error;
  }
};

const login = async (username, password) => {
  console.log("Logging in user:", username);
  try {
    const response = await fetch(`${API_URL}/signin`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });

    const data = await response.json();
    console.log("Login response:", data);

    if (response.ok && data.token) {
      localStorage.setItem("user", JSON.stringify(data));
    } else {
        throw new Error(data.message || "Login failed");
    }

    return data;
  } catch (error) {
    console.error("Login error:", error);
    throw error;
  }
};

const logout = () => {
  console.log("Logging out");
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const authService = {
  register,
  login,
  logout,
  getCurrentUser,
};

export default authService;
