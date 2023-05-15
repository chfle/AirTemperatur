/**
 * Redirects the user to a new URL based on the selected range value.
 * @param {string} val - The selected range value.
 */
const getByEnum = (val) => {
   window.location.href = "/?range="+val
}

/**
 * Calculates the average of an array of numbers.
 * @param {Array} data - The array of numbers.
 * @returns {number} The average value.
 */
const calculateAverage = (data) => {
   const sum = data.reduce((acc, value) => acc + value, 0);
   return sum / data.length;
}

/**
 * Calculates the standard deviation of an array of numbers.
 * @param {Array} data - The array of numbers.
 * @returns {number} The standard deviation value.
 */
const calculateStandardDeviation = (data) => {
   const average = calculateAverage(data);
   const squaredDifferences = data.map(value => (value - average) ** 2);
   const sumSquaredDifferences = squaredDifferences.reduce((acc, value) => acc + value, 0);
   const variance = sumSquaredDifferences / data.length;
   return Math.sqrt(variance);
}