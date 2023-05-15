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

function calculateThreshold(numbers) {
   const average = numbers.reduce((sum, num) => sum + num, 0) / numbers.length;
   const squaredDifferences = numbers.map(num => Math.pow(num - average, 2));
   const variance = squaredDifferences.reduce((sum, squaredDiff) => sum + squaredDiff, 0) / numbers.length;
   const standardDeviation = Math.sqrt(variance);
   return average + 2 * standardDeviation;
}